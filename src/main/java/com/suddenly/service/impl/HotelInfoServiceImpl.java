package com.suddenly.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suddenly.base.PageDto;
import com.suddenly.entity.HotelInfo;
import com.suddenly.entity.UserInfo;
import com.suddenly.entity.request.HotelDto;
import com.suddenly.entity.request.HotelInfoDto;
import com.suddenly.entity.request.HotelPageDto;
import com.suddenly.entity.request.HotelVoPageDto;
import com.suddenly.entity.response.HotelInfoVo;
import com.suddenly.entity.response.HotelVo;
import com.suddenly.enums.OrderStatusEnum;
import com.suddenly.exception.CustomizeException;
import com.suddenly.mapper.HotelInfoMapper;
import com.suddenly.mapper.OrderInfoMapper;
import com.suddenly.mapper.UserInfoMapper;
import com.suddenly.responseResult.ResultEnum;
import com.suddenly.service.HotelInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HotelInfoServiceImpl implements HotelInfoService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private HotelInfoMapper hotelInfoMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public PageInfo<HotelInfoVo> queryPage(HotelPageDto hotelPageDto) throws CustomizeException {
        try {
            PageHelper.startPage(hotelPageDto.getPageNo(), hotelPageDto.getPageSize(), hotelPageDto.getSort());
            HotelInfoDto hotelInfoDto = new HotelInfoDto();
            BeanUtils.copyProperties(hotelPageDto, hotelInfoDto);
            List<HotelInfoVo> hotelInfoVos = hotelInfoMapper.queryAllList(hotelInfoDto);
            hotelInfoVos.stream().forEach(hotelInfoVo -> {
                Map<String, Object> normalMap = orderInfoMapper.queryOrderCount(hotelPageDto.getUserId(), hotelInfoVo.getId(), OrderStatusEnum.ALREADY_CARRY_OUT.getValue());
                Map<String, Object> revokeMap = orderInfoMapper.queryOrderCount(hotelPageDto.getUserId(), hotelInfoVo.getId(), OrderStatusEnum.ALREADY_REVOKE.getValue());
                Map<String, Object> exceptionMap = orderInfoMapper.queryOrderCount(hotelPageDto.getUserId(), hotelInfoVo.getId(), OrderStatusEnum.ABNORMAL.getValue());
                hotelInfoVo.setNormalOrderCount(Integer.valueOf(normalMap.get("quantity") + ""));
                hotelInfoVo.setRevokeOrderCount(Integer.valueOf(revokeMap.get("quantity") + ""));
                hotelInfoVo.setExceptionOrderCount(Integer.valueOf(exceptionMap.get("quantity") + ""));
            });
            PageInfo<HotelInfoVo> pageInfos = new PageInfo<>(hotelInfoVos);
            return pageInfos;
        } catch (CustomizeException e) {
            logger.error("查询酒店失败");
            throw new CustomizeException(ResultEnum.QUERY_HOTEL_FAILURE);
        }
    }

    @Override
    public HotelInfo queryDetails(Long id) throws CustomizeException {
        try {
            return hotelInfoMapper.queryById(id);
        } catch (CustomizeException e) {
            logger.error("查询酒店失败");
            throw new CustomizeException(ResultEnum.QUERY_HOTEL_FAILURE);
        }
    }

    @Override
    public PageInfo<HotelInfoVo> queryBookedHotel(PageDto pageDto) throws CustomizeException {
        try {
            PageHelper.startPage(pageDto.getPageNo(), pageDto.getPageSize());
            ArrayList<HotelInfoVo> hotelInfoVos = new ArrayList<>();
            List<HotelInfo> hotelInfos = hotelInfoMapper.queryBookedHotel(pageDto.getId());
            hotelInfos.stream().forEach(hotelInfo -> {
                HotelInfoVo hotelInfoVo = new HotelInfoVo();
                BeanUtils.copyProperties(hotelInfo, hotelInfoVo);
                Map<String, Object> normalMap = orderInfoMapper.queryOrderCount(pageDto.getId(), hotelInfoVo.getId(), OrderStatusEnum.ALREADY_CARRY_OUT.getValue());
                Map<String, Object> revokeMap = orderInfoMapper.queryOrderCount(pageDto.getId(), hotelInfoVo.getId(), OrderStatusEnum.ALREADY_REVOKE.getValue());
                Map<String, Object> exceptionMap = orderInfoMapper.queryOrderCount(pageDto.getId(), hotelInfoVo.getId(), OrderStatusEnum.ABNORMAL.getValue());
                hotelInfoVo.setNormalOrderCount(Integer.valueOf(normalMap.get("quantity") + ""));
                hotelInfoVo.setRevokeOrderCount(Integer.valueOf(revokeMap.get("quantity") + ""));
                hotelInfoVo.setExceptionOrderCount(Integer.valueOf(exceptionMap.get("quantity") + ""));
                hotelInfoVos.add(hotelInfoVo);
            });
            PageInfo pageInfo = new PageInfo(hotelInfos);
            pageInfo.setList(hotelInfoVos);
            return pageInfo;
        } catch (CustomizeException e) {
            logger.error("查询预定过的酒店失败");
            throw new CustomizeException(ResultEnum.QUERY_BOOKED_HOTEL_FAILURE);
        }
    }

    @Override
    public Boolean updateHotel(HotelDto hotelDto) throws CustomizeException {
        try {
            HotelInfo hotelInfo = new HotelInfo();
            BeanUtils.copyProperties(hotelDto, hotelInfo);
            hotelInfoMapper.updateHotel(hotelInfo);
            return true;
        } catch (CustomizeException e) {
            logger.error("更新酒店信息失败");
            throw new CustomizeException(ResultEnum.UPDATE_HOTEL_INFO_FAILURE);
        }
    }

    @Override
    public PageInfo<HotelVo> queryHotel(HotelVoPageDto hotelVoPageDto) throws CustomizeException {
        try {
            PageHelper.startPage(hotelVoPageDto.getPageNo(), hotelVoPageDto.getPageSize());
            List<HotelVo> hotelVos = new ArrayList<>();
            List<HotelInfoVo> hotelInfoVos = hotelInfoMapper.queryAll();
            hotelInfoVos.stream().forEach(hotelInfoVo -> {
                HotelVo hotelVo = new HotelVo();
                BeanUtils.copyProperties(hotelInfoVo, hotelVo);
                hotelVos.add(hotelVo);
            });

            List<HotelVo> hotelVoList =  hotelVos.stream().map(hotelVo -> {
                HotelVo vo = new HotelVo();
                List<UserInfo> userInfos = userInfoMapper.queryHotelStaff(hotelVo.getId());
                vo.setId(hotelVo.getId());
                vo.setHotelName(hotelVo.getHotelName());
                if (!userInfos.isEmpty()) {
                    vo.setIsExist("1");
                    vo.setUserId(userInfos.get(0).getId());
                    return vo;
                } else {
                    vo.setIsExist("0");
                    return vo;
                }
            }).collect(Collectors.toList());
            if ("1".equals(hotelVoPageDto.getIsExist())) {
                hotelVoList.stream().forEach(hotelVo -> {
                    if ("0".equals(hotelVo.getIsExist())) {
                        hotelVoList.remove(hotelVo);
                    }
                });
            }
            if ("0".equals(hotelVoPageDto.getIsExist())) {
                hotelVoList.stream().forEach(hotelVo -> {
                    if ("1".equals(hotelVo.getIsExist())) {
                        hotelVoList.remove(hotelVo);
                    }
                });
            }
            PageInfo pageInfo = new PageInfo(hotelInfoVos);
            pageInfo.setList(hotelVoList);
            return pageInfo;
        } catch (CustomizeException e) {
            logger.error("查询酒店是否存在工作人员失败");
            throw new CustomizeException(ResultEnum.QUERY_HOTEL_IS_EXISTED_STAFF_FAILURE);
        }
    }

}
