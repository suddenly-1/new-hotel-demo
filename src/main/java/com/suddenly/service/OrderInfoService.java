package com.suddenly.service;

import com.github.pagehelper.PageInfo;
import com.suddenly.entity.request.CurrentPageDto;
import com.suddenly.entity.request.EvaluationDto;
import com.suddenly.entity.request.OrderInfoDto;
import com.suddenly.entity.request.OrderPageDto;
import com.suddenly.entity.response.OrderInfoVo;
import com.suddenly.exception.CustomizeException;

import java.util.Map;

public interface OrderInfoService {

    /**
     * 查询
     * @param orderNumber
     * @return OrderInfoVo
     */
    OrderInfoVo queryByOrderNumber(String orderNumber) throws CustomizeException;

    /**
     * 分页查询
     * @param orderPageDto
     * @return Page<OrderInfoVo>
     */
    PageInfo<OrderInfoVo> queryPage(OrderPageDto orderPageDto) throws CustomizeException;

    /**
     * 生成订单
     * @Param orderInfoDto
     * @return Boolean
     */
    Boolean create(OrderInfoDto orderInfoDto) throws CustomizeException;

    /**
     * 撤销订单
     * @param orderNumber
     * @param status
     * @return Boolean
     */
    Boolean revoke(String orderNumber, Integer status) throws CustomizeException;

    /**
     * 执行订单
     * @param orderNumber
     * @param status
     * @return Boolean
     */
    Boolean carriedOut(String orderNumber, Integer status) throws CustomizeException;

    /**
     * 执行异常订单
     * @param orderNumber
     * @param status
     * @return Boolean
     */
    Boolean carryOutExceptionOrder(String orderNumber, Integer status) throws CustomizeException;

    /**
     * 查询用户在酒店的订单数量
     * @Param userId
     * @Param hotelId
     * @Param status
     */
    Map<String, Object> queryOrderCount(Long userId, Long hotelId, String status) throws CustomizeException;

    /**
     * 查询用户在当前酒店的订单
     * @Param currentPageDto
     */
    PageInfo<OrderInfoVo> queryCurrentOrder(CurrentPageDto currentPageDto) throws CustomizeException;

    /**
     * 评价已完成的订单
     */
    Boolean evaluation(EvaluationDto evaluationDto) throws CustomizeException;

    /**
     * 完成订单
     * @param orderNumber
     * @param status
     * @return Boolean
     */
    Boolean carryOutOrder(String orderNumber, Integer status) throws CustomizeException;

    /**
     * 查询当天订单
     * @param orderPageDto
     */
    PageInfo<OrderInfoVo> queryThatDay(OrderPageDto orderPageDto) throws CustomizeException;

}
