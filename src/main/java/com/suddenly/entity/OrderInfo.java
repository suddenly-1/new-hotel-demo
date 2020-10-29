package com.suddenly.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("OrderInfo")
public class OrderInfo implements Serializable {

    private Long id;

    private String orderNumber;

    private String phone;

    private String userName;

    private Long userId;

    private Long hotelId;

    private Long roomId;

    private String roomNumber;

    private String hotelName;

    private Date generationDate;

    private Date startDate;

    private Date endDate;

    private Date latestDate;

    private Date actualCheckinTime;

    private Date actualTime;

    private String roomType;

    private Integer rooms;

    private Integer number;

    private Integer child;

    private String status;

    private Double amount;

    private Double score;

    private String comment;

    private Date revocationTime;

    private Double discount;

}
