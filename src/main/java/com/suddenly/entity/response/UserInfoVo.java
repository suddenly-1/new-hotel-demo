package com.suddenly.entity.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.suddenly.base.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfoVo extends BaseDto {

    private String userName;

    private String accountNumber;

    private String password;

    private String phone;

    private Double credit;

    private String vip;

    private String userType;

    private Date birthday;

    private String companyName;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long hotelId;
}
