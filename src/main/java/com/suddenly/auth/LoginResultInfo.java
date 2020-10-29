package com.suddenly.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResultInfo implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String token; //登录成功后授权token

    private String account;

    private String mobile;

    private String email;

    private String realName;

    private Integer isAdmin; //是否是管理员 0：否；1：是

    private String last_login_time;

    private String synchronised_time;

    private String job;

    private String changePass;

}
