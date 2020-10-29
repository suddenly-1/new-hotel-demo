package com.suddenly.mapper;

import com.suddenly.entity.HotelRoom;
import com.suddenly.entity.request.HotelRoomDto;
import com.suddenly.exception.CustomizeException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelRoomMapper {

    /**
     * 根据酒店id查询该酒店房间
     * @Param hotelId
     */
    List<HotelRoom> queryByHotelId(@Param("hotelId") Long hotelId);

    /**
     * 根据房间id查询
     * @Param hotelId
     */
    HotelRoom queryByroomId(@Param("roomId") Long roomId);

    /**
     * 修改房型
     * @Paam hotelRoom
     */
    int update(HotelRoom hotelRoom);

    /**
     * 删除房型
     * @Param roomId
     */
    int delete(@Param("roomId") Long roomId);

    /**
     * 新增房型
     * @Param hotelRoomDto
     */
    Boolean insert(HotelRoom hotelRoom);

}
