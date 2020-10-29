package com.suddenly.mapper;

import com.suddenly.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface OrderInfoMapper {

    /**
     * 根据订单号查询
     * @param orderNumber
     * @return OrderInfoVo
     */
    OrderInfo queryByOrderNumber(@Param("orderNumber") String orderNumber);

    /**
     * 查询订单
     * @param orderInfo
     * @return List<OrderInfo>
     */
    List<OrderInfo> query(OrderInfo orderInfo);

    /**
     * 新增订单
     * @param orderInfo
     */
    void insert(OrderInfo orderInfo);

    /**
     * 修改订单
     * @param orderInfo
     */
    void update(OrderInfo orderInfo);

    /**
     * 查询用户在酒店的订单数量
     * @Param userId
     * @Param hotelId
     * @Param status
     */
    Map<String, Object> queryOrderCount(@Param("userId") Long userId, @Param("hotelId") Long hotelId, @Param("status") String status);

    /**
     * 查询当天订单
     */
    List<OrderInfo> queryThatDay(@Param("status") String status);
}
