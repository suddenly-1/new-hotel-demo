package com.suddenly.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("订单Dto")
@Data
public class OrderInfoDto implements Serializable {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("酒店id")
    private Long hotelId;

    @ApiModelProperty("房间id")
    private Long roomId;

    @ApiModelProperty("房号")
    private String roomNumber;

    @ApiModelProperty("酒店名")
    private String hotelName;

    @ApiModelProperty("入住日期")
    private String startDate;

    @ApiModelProperty("退房日期")
    private String endDate;

    @ApiModelProperty("房型")
    private String roomType;

    @ApiModelProperty("间数")
    private Integer rooms;

    @ApiModelProperty("人数")
    private Integer number;

    @ApiModelProperty("有无儿童")
    private Integer child;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("金额")
    private Double amount;

}
