package com.suddenly.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("UserInfo")
public class UserInfo implements Serializable {

    private Long id;

    private String userName;

    private String accountNumber;

    private String password;

    private String phone;

    private Double credit;

    private String vip;

    private String userType;

    private Date birthday;

    private String companyName;

    private Long hotelId;
}
