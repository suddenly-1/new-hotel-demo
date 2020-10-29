package com.suddenly.entity.response;

import com.suddenly.entity.HotelRoom;
import lombok.Data;

@Data
public class HotelRoomVo extends HotelRoom {

    private Integer remainingRoomQuantity;

    private String roomStatus;

}
