package com.suddenly.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.suddenly.base.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CurrentPageDto extends PageDto {

    @ApiModelProperty("/用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty("/酒店id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long hotelId;

    @ApiModelProperty("状态")
    private Integer status;

}
