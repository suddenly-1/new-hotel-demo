package com.suddenly.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("酒店基本信息")
@Data
public class HotelDto implements Serializable {

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("所属商圈")
    private String businessDistrict;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("设施服务")
    private String facilities;

    @ApiModelProperty("星级")
    private Integer star;

    @ApiModelProperty("最低价格")
    private Double price;

}
