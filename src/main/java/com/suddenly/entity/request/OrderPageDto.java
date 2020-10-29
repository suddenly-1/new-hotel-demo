package com.suddenly.entity.request;

import com.suddenly.base.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderPageDto extends PageDto {

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("酒店Id")
    private Long hotelId;

    @ApiModelProperty("用户Id")
    private Long userId;

}
