package com.suddenly.service;

import com.suddenly.entity.HotelRoom;
import com.suddenly.entity.request.HotelRoomDto;
import com.suddenly.exception.CustomizeException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HotelRoomService {

    /**
     * 查询该酒店房间
     * @Param hotelId
     */
    List<HotelRoom> queryByHotelId(@Param("hotelId") Long hotelId) throws CustomizeException;

    /**
     * 修改酒店房型
     * @Param hotelRoom
     */
    Boolean update(HotelRoom hotelRoom) throws CustomizeException;

    /**
     * 删除酒店房型
     * @Param hotelRoom
     */
    Boolean delete(Long roomId) throws CustomizeException;

    /**
     * 新增房型
     * @Param hotelRoomDto
     */
    Boolean insert(HotelRoomDto hotelRoomDto) throws CustomizeException;

}
