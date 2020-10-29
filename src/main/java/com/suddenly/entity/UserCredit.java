package com.suddenly.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Alias("UserCredit")
@Data
public class UserCredit implements Serializable {

    private Long id;

    private Long userId;

    private Date time;

    private String orderNumber;

    private String action;

    private String creditChange;

    private Double creditResult;

}
