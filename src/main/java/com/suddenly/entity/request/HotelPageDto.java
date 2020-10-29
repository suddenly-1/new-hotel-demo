package com.suddenly.entity.request;

import com.suddenly.base.PageDto;
import lombok.Data;

@Data
public class HotelPageDto extends PageDto {

    private Long userId;

    private String address;

    private String businessDistrict;

    private String startTime;

    private String endTime;

    private String hotelName;

    private Integer star;

    private Double minScore;

    private Double maxScore;

    private Double minPrice;

    private Double maxPrice;

    private String roomType;

    private Integer roomQuantity;

    private String sort;

}
