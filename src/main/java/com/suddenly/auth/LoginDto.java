package com.suddenly.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Api(tags = "登录")
@Data
public class LoginDto implements Serializable {

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("图片验证码")
    private String verCode;

    @ApiModelProperty("短信验证码")
    private String smsCode;

    @ApiModelProperty("登录方式。1：账号登录；2：手机登录；3：邮箱登录")
    private Integer loginType;

}
