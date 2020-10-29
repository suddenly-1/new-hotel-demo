package com.suddenly.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("HotelInfo")
@Data
public class HotelInfo implements Serializable {

    private Long id;

    private String hotelName;

    private String address;

    private String businessDistrict;

    private String introduction;

    private String facilities;

    private Integer star;

    private Double score;

    private Double price;

}
