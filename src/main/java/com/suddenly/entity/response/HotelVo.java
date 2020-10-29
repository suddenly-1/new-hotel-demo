package com.suddenly.entity.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotelVo implements Serializable {

    private Long id;

    private String hotelName;

    private String isExist;

    private Long userId;

}
