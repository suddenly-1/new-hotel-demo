package com.suddenly.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("HotelRoom")
@Data
public class HotelRoom implements Serializable {

    private Long roomId;

    private Long hotelId;

    private String roomType;

    private Double price;

    private Integer rooms;

    private Double discount;

}
