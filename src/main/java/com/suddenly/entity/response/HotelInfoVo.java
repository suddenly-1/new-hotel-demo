package com.suddenly.entity.response;

import com.suddenly.entity.HotelInfo;
import lombok.Data;

import java.util.List;

@Data
public class HotelInfoVo extends HotelInfo {

    private Integer normalOrderCount;

    private Integer revokeOrderCount;

    private Integer exceptionOrderCount;

    private List<HotelRoomVo> hotelRoomVoList;

}
