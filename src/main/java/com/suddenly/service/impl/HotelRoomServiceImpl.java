package com.suddenly.service.impl;

import com.suddenly.entity.HotelRoom;
import com.suddenly.entity.request.HotelRoomDto;
import com.suddenly.exception.CustomizeException;
import com.suddenly.mapper.HotelRoomMapper;
import com.suddenly.responseResult.ResultEnum;
import com.suddenly.service.HotelRoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRoomServiceImpl implements HotelRoomService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Override
    public List<HotelRoom> queryByHotelId(Long hotelId) {
        try {
            List<HotelRoom> hotelRooms = hotelRoomMapper.queryByHotelId(hotelId);
            return hotelRooms;
        } catch (CustomizeException e) {
            logger.error("查询酒店房型失败");
            throw new CustomizeException(ResultEnum.QUERY_HOTEL_TYPE_FAILURE);
        }
    }

    @Override
    public Boolean update(HotelRoom hotelRoom) throws CustomizeException {
        try {
            hotelRoomMapper.update(hotelRoom);
            return true;
        } catch (CustomizeException e) {
            logger.error("修改酒店房型失败");
            throw new CustomizeException(ResultEnum.UPDATE_HOTEL_TYPE_FAILURE);
        }
    }

    @Override
    public Boolean delete(Long roomId) throws CustomizeException {
        try {
            hotelRoomMapper.delete(roomId);
            return true;
        } catch (CustomizeException e) {
            logger.error("删除酒店房型失败");
            throw new CustomizeException(ResultEnum.DELETE_HOTEL_ROOM_FAILURE);
        }
    }

    @Override
    public Boolean insert(HotelRoomDto hotelRoomDto) throws CustomizeException {
        try {
            HotelRoom hotelRoom = new HotelRoom();
            BeanUtils.copyProperties(hotelRoomDto, hotelRoom);
            hotelRoomMapper.insert(hotelRoom);
            return true;
        } catch (CustomizeException e) {
            logger.error("新增房型失败");
            throw new CustomizeException(ResultEnum.ADD_ROOM_TYPE_FAILURE);
        }
    }

}
