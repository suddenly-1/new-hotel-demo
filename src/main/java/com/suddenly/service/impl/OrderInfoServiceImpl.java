package com.suddenly.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suddenly.entity.HotelRoom;
import com.suddenly.entity.OrderInfo;
import com.suddenly.entity.UserCredit;
import com.suddenly.entity.UserInfo;
import com.suddenly.entity.request.CurrentPageDto;
import com.suddenly.entity.request.EvaluationDto;
import com.suddenly.entity.request.OrderInfoDto;
import com.suddenly.entity.request.OrderPageDto;
import com.suddenly.entity.response.OrderInfoVo;
import com.suddenly.enums.CompanyNameEnum;
import com.suddenly.enums.OrderStatusEnum;
import com.suddenly.enums.VipEnum;
import com.suddenly.exception.CustomizeException;
import com.suddenly.mapper.HotelRoomMapper;
import com.suddenly.mapper.OrderInfoMapper;
import com.suddenly.mapper.UserCreditMapper;
import com.suddenly.mapper.UserInfoMapper;
import com.suddenly.responseResult.ResultEnum;
import com.suddenly.service.OrderInfoService;
import com.suddenly.utils.MyDateUtil;
import com.suddenly.utils.UUIDUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserCreditMapper userCreditMapper;

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Override
    public OrderInfoVo queryByOrderNumber(String orderNumber) throws CustomizeException{
        try {
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            OrderInfo orderInfo = orderInfoMapper.queryByOrderNumber(orderNumber);
            BeanUtils.copyProperties(orderInfo, orderInfoVo);
            return orderInfoVo;
        } catch (CustomizeException e) {
            logger.error("查询订单失败");
            throw new CustomizeException(ResultEnum.QUERY_ORDER_FAILURE);
        }
    }

    @Override
    public PageInfo<OrderInfoVo> queryPage(OrderPageDto orderPageDto) throws CustomizeException{
        try {
            PageHelper.startPage(orderPageDto.getPageNo(), orderPageDto.getPageSize());
            OrderInfo orderInfo = new OrderInfo();
            BeanUtils.copyProperties(orderPageDto, orderInfo);
            Map<Integer, String> orderStatusMap = OrderStatusEnum.getOrderStatusMap();
            if (!orderStatusMap.containsKey(orderPageDto.getStatus())) {
                throw new CustomizeException(ResultEnum.ORDER_STATUS_DOES_NOT_EXIST);
            }
            if (orderStatusMap.containsKey(orderPageDto.getStatus())) {
                orderInfo.setStatus(orderStatusMap.get(orderPageDto.getStatus()));
            }
            List<OrderInfo> orderInfos = orderInfoMapper.query(orderInfo);
            List<OrderInfoVo> orderInfoVos = new ArrayList<>();
            orderInfos.stream().forEach(order -> {
                OrderInfoVo orderInfoVo = new OrderInfoVo();
                BeanUtils.copyProperties(order, orderInfoVo);
                orderInfoVos.add(orderInfoVo);
            });
            PageInfo pageInfo = new PageInfo(orderInfos);
            pageInfo.setList(orderInfoVos);
            return pageInfo;
        } catch (CustomizeException e) {
            logger.error(e.getResultEnum());
            throw new CustomizeException(e, e.getResultEnum());
        } catch (Exception e) {
            logger.error("查询订单失败");
            throw new CustomizeException(ResultEnum.QUERY_ORDER_FAILURE);
        }
    }

    @Override
    public Boolean create(OrderInfoDto orderInfoDto) throws CustomizeException {
        try {
            HotelRoom hotelRoom = hotelRoomMapper.queryByroomId(orderInfoDto.getRoomId());
            UserInfo userInfo = userInfoMapper.queryById(orderInfoDto.getUserId());
            if (userInfo.getCredit() <= 0) {
                throw new CustomizeException(ResultEnum.CREDIT_LOW);
            }
            Date getNowDate = new Date();
            OrderInfo orderInfo = new OrderInfo();
            BeanUtils.copyProperties(orderInfoDto, orderInfo);
            orderInfo.setStartDate(MyDateUtil.stringConvertDate(orderInfoDto.getStartDate()));
            orderInfo.setEndDate(MyDateUtil.stringConvertDate(orderInfoDto.getEndDate()));
            orderInfo.setOrderNumber(UUIDUtil.getUUID19());
            orderInfo.setGenerationDate(getNowDate);
            String nowDate = orderInfoDto.getStartDate().substring(0, 10);
            Long nowTimestamp = MyDateUtil.stringConvertTimestamp(nowDate, "yyyy-MM-dd");
            long lastTimestamp = nowTimestamp + (1000 * 60 * 60 * 22);
            orderInfo.setLatestDate(new Date(lastTimestamp));
            if (hotelRoom.getDiscount() != null) {
                orderInfo.setAmount(orderInfoDto.getAmount() * hotelRoom.getDiscount());
                orderInfo.setDiscount(hotelRoom.getDiscount());
            }

            UserInfo userInfo1 = userInfoMapper.queryById(orderInfoDto.getUserId());
            Map<Integer, String> companyNameMap = CompanyNameEnum.getCompanyNameMap();
            if (companyNameMap.containsValue(userInfo1.getCompanyName())) {
                orderInfoDto.setAmount(orderInfoDto.getAmount() * 0.9);
            }

            orderInfoMapper.insert(orderInfo);

            long interval = lastTimestamp - getNowDate.getTime();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    OrderInfo order = orderInfoMapper.queryByOrderNumber(orderInfo.getOrderNumber());
                    if (OrderStatusEnum.NOT_CARRIED_OUT.getValue().equals(order.getStatus())) {
                        OrderInfo orderInfo = new OrderInfo();
                        orderInfo.setOrderNumber(order.getOrderNumber());
                        orderInfo.setStatus(OrderStatusEnum.ABNORMAL.getValue());
                        orderInfoMapper.update(orderInfo);

                        UserInfo userInfo1 = userInfoMapper.queryById(order.getUserId());
                        updateCredit(order.getOrderNumber(), OrderStatusEnum.ABNORMAL.getValue(), userInfo1.getCredit() - order.getAmount(), "-");
                    }
                }
            }, interval);
            return true;
        } catch (CustomizeException e) {
            logger.error(e.getResultEnum());
            throw new CustomizeException(e, e.getResultEnum());
        } catch (Exception e) {
            logger.error("新增订单失败");
            throw new CustomizeException(ResultEnum.CREATE_ORDER_EXIST);
        }
    }

    @Override
    public Boolean revoke(String orderNumber, Integer status) throws CustomizeException {
        try {
            Map<Integer, String> orderStatusMap = OrderStatusEnum.getOrderStatusMap();
            OrderInfo order = orderInfoMapper.queryByOrderNumber(orderNumber);
            if (!OrderStatusEnum.NOT_CARRIED_OUT.getValue().equals(order.getStatus())) {
                throw new CustomizeException(ResultEnum.ONLY_CAN_REVOKE_NOT_CARRIED_OUT_ORDER);
            }
            if (!orderStatusMap.containsKey(status)) {
                throw new CustomizeException(ResultEnum.ORDER_STATUS_DOES_NOT_EXIST);
            }
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderNumber(orderNumber);
            orderInfo.setStatus(orderStatusMap.get(status));
            orderInfo.setRevocationTime(new Date());
            orderInfoMapper.update(orderInfo);

            OrderInfo orderInfo1 = orderInfoMapper.queryByOrderNumber(orderNumber);
            long latestTimestamp = orderInfo1.getLatestDate().getTime();
            long nowTimestamp = new Date().getTime();
            long resultTimestamp = latestTimestamp - nowTimestamp;
            if (resultTimestamp < (1000 * 60 * 60 *6)) {
                Double resAmount = orderInfo1.getAmount() / 2;
                UserInfo userInfo = userInfoMapper.queryById(order.getUserId());
                Double res = userInfo.getCredit() - resAmount;
                updateCredit(orderNumber, orderStatusMap.get(status), res, "-", resAmount);
            }
            return true;
        } catch (CustomizeException e) {
            logger.error(e.getResultEnum());
            throw new CustomizeException(e, e.getResultEnum());
        } catch (Exception e) {
            logger.error("撤销订单失败");
            throw new CustomizeException(ResultEnum.REVOKE_ORDER_FAILURE);
        }
    }

    @Override
    public Boolean carriedOut(String orderNumber, Integer status) throws CustomizeException {
        try {
            Map<Integer, String> orderStatusMap = OrderStatusEnum.getOrderStatusMap();
            OrderInfo order = orderInfoMapper.queryByOrderNumber(orderNumber);
            if (!OrderStatusEnum.NOT_CARRIED_OUT.getValue().equals(order.getStatus())) {
                throw new CustomizeException(ResultEnum.ONLY_CAN_CARRY_OUT_NOT_CARRIED_OUT_ORDER);
            }
            if (!orderStatusMap.containsKey(status)) {
                throw new CustomizeException(ResultEnum.ORDER_STATUS_DOES_NOT_EXIST);
            }
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderNumber(orderNumber);
            orderInfo.setStatus(orderStatusMap.get(status));
            orderInfo.setActualCheckinTime(new Date());
            orderInfoMapper.update(orderInfo);
            return true;
        } catch (CustomizeException e) {
            logger.error(e.getResultEnum());
            throw new CustomizeException(e, e.getResultEnum());
        } catch (Exception e) {
            logger.error("执行订单失败");
            throw new CustomizeException(ResultEnum.CARRIED_OUT_ORDER_FAILURE);
        }
    }

    @Override
    public Boolean carryOutExceptionOrder(String orderNumber, Integer status) throws CustomizeException {
        try {
            Map<Integer, String> orderStatusMap = OrderStatusEnum.getOrderStatusMap();
            OrderInfo order = orderInfoMapper.queryByOrderNumber(orderNumber);
            if (!OrderStatusEnum.ABNORMAL.getValue().equals(order.getStatus())) {
                throw new CustomizeException(ResultEnum.ONLY_CAN_CARRY_OUT_EXCEPTION_ORDER);
            }
            if (!orderStatusMap.containsKey(status)) {
                throw new CustomizeException(ResultEnum.ORDER_STATUS_DOES_NOT_EXIST);
            }
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderNumber(orderNumber);
            orderInfo.setStatus(orderStatusMap.get(status));
            orderInfo.setActualCheckinTime(new Date());
            orderInfoMapper.update(orderInfo);

            UserInfo userInfo = userInfoMapper.queryById(order.getUserId());
            Double res = userInfo.getCredit() + order.getAmount();
            updateCredit(orderNumber, orderStatusMap.get(status), res, "+");
            return true;
        } catch (CustomizeException e) {
            logger.error(e.getResultEnum());
            throw new CustomizeException(e, e.getResultEnum());
        } catch (Exception e) {
            logger.error("执行异常订单失败");
            throw new CustomizeException(ResultEnum.CARRY_OUT_EXCEPTION_ORDER_FAILURE);
        }
    }

    @Override
    public Map<String, Object> queryOrderCount(Long userId, Long hotelId, String status) {
        try {
            return orderInfoMapper.queryOrderCount(userId, hotelId, status);
        } catch (CustomizeException e) {
            logger.error("查询订单数量失败");
            throw new CustomizeException(ResultEnum.QUERY_ORDER_QUANTITY_FAILURE);
        }
    }

    @Override
    public PageInfo<OrderInfoVo> queryCurrentOrder(CurrentPageDto currentPageDto) throws CustomizeException {
        try {
            PageHelper.startPage(currentPageDto.getPageNo(), currentPageDto.getPageSize());
            OrderInfo orderInfo = new OrderInfo();
            BeanUtils.copyProperties(currentPageDto, orderInfo);
            Map<Integer, String> orderStatusMap = OrderStatusEnum.getOrderStatusMap();
            if (!orderStatusMap.containsKey(currentPageDto.getStatus())) {
                throw new CustomizeException(ResultEnum.ORDER_STATUS_DOES_NOT_EXIST);
            }
            orderInfo.setStatus(orderStatusMap.get(currentPageDto.getStatus()));
            List<OrderInfoVo> orderInfoVos = new ArrayList<>();
            List<OrderInfo> orderInfoList = orderInfoMapper.query(orderInfo);
            orderInfoList.stream().forEach(orderInfo1 -> {
                OrderInfoVo orderInfoVo = new OrderInfoVo();
                BeanUtils.copyProperties(orderInfo1, orderInfoVo);
                orderInfoVos.add(orderInfoVo);
            });
            PageInfo pageInfo = new PageInfo(orderInfoList);
            pageInfo.setList(orderInfoVos);
            return pageInfo;
        } catch (CustomizeException e) {
            logger.error(e.getResultEnum());
            throw new CustomizeException(e, e.getResultEnum());
        } catch (Exception e) {
            logger.error("查询用户在当前酒店的订单失败");
            throw new CustomizeException(ResultEnum.QUERY_USER_CURRENT_HOTEL_ORDER_FAILURE);
        }
    }

    @Override
    public Boolean evaluation(EvaluationDto evaluationDto) throws CustomizeException {
        try {
            OrderInfo orderInfo = orderInfoMapper.queryByOrderNumber(evaluationDto.getOrderNumber());
            if (!OrderStatusEnum.ALREADY_CARRY_OUT.getValue().equals(orderInfo.getStatus())) {
                throw new CustomizeException(ResultEnum.CAN_ONLY_EVALUATION_ALREADY_CARRY_OUT_ORDER);
            }
            OrderInfo orderInfo1 = new OrderInfo();
            BeanUtils.copyProperties(evaluationDto, orderInfo1);
            orderInfoMapper.update(orderInfo1);
            return true;
        } catch (CustomizeException e) {
            logger.error(e.getResultEnum());
            throw new CustomizeException(e, e.getResultEnum());
        } catch (Exception e) {
            logger.error("评价失败");
            throw new CustomizeException(ResultEnum.EVALUATION_FAILURE);
        }
    }



    @Override
    @Transactional
    public Boolean carryOutOrder(String orderNumber, Integer status) throws CustomizeException {
        try {
            Map<Integer, String> orderStatusMap = OrderStatusEnum.getOrderStatusMap();
            if (!orderStatusMap.containsKey(status)) {
                throw new CustomizeException(ResultEnum.ORDER_STATUS_DOES_NOT_EXIST);
            }
            OrderInfo info = orderInfoMapper.queryByOrderNumber(orderNumber);
            if (!OrderStatusEnum.ALREADY_CARRIED_OUT.getValue().equals(info.getStatus())) {
                throw new CustomizeException(ResultEnum.ONLY_CAN_CARRY_OUT_CARRIED_OUT_ORDER);
            }
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderNumber(orderNumber);
            orderInfo.setStatus(orderStatusMap.get(status));
            orderInfo.setActualTime(new Date());
            orderInfoMapper.update(orderInfo);

            OrderInfo orderInfo1 = orderInfoMapper.queryByOrderNumber(orderNumber);
            UserInfo userInfo = userInfoMapper.queryById(orderInfo1.getUserId());
            Double res = userInfo.getCredit() + orderInfo1.getAmount();
            updateCredit(orderNumber, orderStatusMap.get(status), res, "+");
            return true;
        } catch (CustomizeException e) {
            logger.error(e.getResultEnum());
            throw new CustomizeException(e, e.getResultEnum());
        } catch (Exception e) {
            logger.error("完成订单失败");
            throw new CustomizeException(ResultEnum.CARRY_OUT_ORDER_FAILURE);
        }
    }

    @Override
    public PageInfo<OrderInfoVo> queryThatDay(OrderPageDto orderPageDto) throws CustomizeException {
        try {
            PageHelper.startPage(orderPageDto.getPageNo(), orderPageDto.getPageSize());
            Map<Integer, String> orderStatusMap = OrderStatusEnum.getOrderStatusMap();
            List<OrderInfo> orderInfos = orderInfoMapper.queryThatDay(orderStatusMap.get(orderPageDto.getStatus()));
            ArrayList<OrderInfoVo> orderInfoVos = new ArrayList<>();
            orderInfos.stream().forEach(orderInfo -> {
                OrderInfoVo orderInfoVo = new OrderInfoVo();
                BeanUtils.copyProperties(orderInfo, orderInfoVo);
                orderInfoVos.add(orderInfoVo);
            });
            PageInfo pageInfo = new PageInfo(orderInfos);
            pageInfo.setList(orderInfoVos);
            return pageInfo;
        } catch (CustomizeException e) {
            logger.error("查询当天订单失败");
            throw new CustomizeException(ResultEnum.QUERY_THAT_DAY_ORDER_FAILURE);
        }
    }


    private Boolean updateCredit(String orderNumber, String status, Double resultCredit, String str) {
        OrderInfo orderInfo = orderInfoMapper.queryByOrderNumber(orderNumber);
        updateCredit(orderNumber, status, resultCredit, str, orderInfo.getAmount());
        return true;
    }

    private Boolean updateCredit(String orderNumber, String status, Double resultCredit, String str, Double resAmount) {
        OrderInfo orderInfo = orderInfoMapper.queryByOrderNumber(orderNumber);
        UserCredit userCredit = new UserCredit();
        userCredit.setUserId(orderInfo.getUserId());
        userCredit.setTime(new Date());
        userCredit.setOrderNumber(orderNumber);
        userCredit.setAction(status);
        userCredit.setCreditChange(str + resAmount);
        userCredit.setCreditResult(resultCredit);
        userCreditMapper.insert(userCredit);

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(orderInfo.getUserId());
        userInfo1.setCredit(resultCredit);
        if (resultCredit >= 0 && resultCredit < 2000) {
            userInfo1.setVip(VipEnum.ONE.getValue());
        } else if (resultCredit >= 2000 && resultCredit < 4000) {
            userInfo1.setVip(VipEnum.TWO.getValue());
        } else if (resultCredit >= 4000 && resultCredit < 6000) {
            userInfo1.setVip(VipEnum.THREE.getValue());
        } else if (resultCredit >= 6000 && resultCredit < 8000) {
            userInfo1.setVip(VipEnum.FOUR.getValue());
        } else if (resultCredit >= 8000) {
            userInfo1.setVip(VipEnum.FIVES.getValue());
        }
        userInfoMapper.updateUserInfo(userInfo1);
        return true;
    }


}
//i.用户管理（客户、酒店工作人员、网站营销人员）
//        1.查询用户信息，更改用户信息
//        2.查询，更改酒店工作人员信息。
//        3.添加网站营销人员，查询，更改营销人员信息。
//        ii.添加酒店及其工作人员，查询、更改酒店工作人员信息
//        1.需要先添加酒店（名称），才能为其添加工作人员；
//        2.一个酒店只有一个工作人员账号