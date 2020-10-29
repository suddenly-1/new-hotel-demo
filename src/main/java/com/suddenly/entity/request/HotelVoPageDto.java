package com.suddenly.entity.request;

import com.suddenly.base.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HotelVoPageDto extends PageDto {

    @ApiModelProperty("是否存在")
    private String isExist;

}
