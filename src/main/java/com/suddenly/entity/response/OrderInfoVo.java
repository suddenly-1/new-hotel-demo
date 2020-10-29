package com.suddenly.entity.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.suddenly.base.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoVo extends BaseDto {

    private String orderNumber;

    private String phone;

    private String userName;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long userId;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long hotelId;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
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
}
