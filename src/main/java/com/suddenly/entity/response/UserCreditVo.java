package com.suddenly.entity.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.suddenly.base.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class UserCreditVo extends BaseDto {

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long userId;

    private Date time;

    private String orderNumber;

    private String action;

    private String creditChange;

    private Double creditResult;

}
