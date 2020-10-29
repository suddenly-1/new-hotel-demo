package com.suddenly.entity.request;

import com.suddenly.base.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreditPageDto extends PageDto {

    @ApiModelProperty("用户id")
    private Long userId;

}
