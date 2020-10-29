package com.suddenly.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("房型信息")
@Data
public class HotelRoomDto implements Serializable {

    @ApiModelProperty("酒店id")
    private Long hotelId;

    @ApiModelProperty("类型")
    private String roomType;

    @ApiModelProperty("价格")
    private Double price;

    @ApiModelProperty("数量")
    private Integer rooms;

}
