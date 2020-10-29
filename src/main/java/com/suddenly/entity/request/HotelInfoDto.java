package com.suddenly.entity.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotelInfoDto implements Serializable {

    private String address;

    private String hotelName;

    private String startTime;

    private String endTime;

    private String businessDistrict;

    private Integer star;

    private Double minScore;

    private Double maxScore;

    private Double minPrice;

    private Double maxPrice;

    private String roomType;

    private Integer roomQuantity;

}
