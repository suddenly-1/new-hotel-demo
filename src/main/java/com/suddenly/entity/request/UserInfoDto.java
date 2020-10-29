package com.suddenly.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("用户信息Dto")
public class UserInfoDto {

    @ApiModelProperty("id")
    private Long id;

    @NotNull(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String userName;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty("账号")
    private String accountNumber;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("信用")
    private Double credit;

    @ApiModelProperty("vip")
    private String vip;

    @ApiModelProperty("用户类型")
    private String userType;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("公司名")
    private String companyName;

    @ApiModelProperty("酒店id")
    private Long hotelId;

}
