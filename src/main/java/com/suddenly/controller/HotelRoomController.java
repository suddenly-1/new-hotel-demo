package com.suddenly.controller;

import com.suddenly.entity.HotelRoom;
import com.suddenly.entity.request.HotelRoomDto;
import com.suddenly.exception.CustomizeException;
import com.suddenly.responseResult.ResponseResult;
import com.suddenly.service.HotelRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "房型管理")
@RestController
@RequestMapping("/room")
public class HotelRoomController {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private HotelRoomService hotelRoomService;

    @ApiOperation(value = "查询酒店房型", httpMethod = "GET", response = ResponseResult.class)
    @GetMapping("/query")
    public ResponseResult<?> query(@RequestParam Long hotelId) {
        try {
            return ResponseResult.returnSuccess(hotelRoomService.queryByHotelId(hotelId));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "修改房型", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/update")
    public ResponseResult<?> update(@RequestBody HotelRoom hotelRoom) {
        try {
            return ResponseResult.returnSuccess(hotelRoomService.update(hotelRoom));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "删除房型", httpMethod = "GET", response = ResponseResult.class)
    @GetMapping("/delete")
    public ResponseResult<?> delete(@RequestParam Long roomId) {
        try {
            return ResponseResult.returnSuccess(hotelRoomService.delete(roomId));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "新增房型", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/insert")
    public ResponseResult<?> insert(@RequestBody HotelRoomDto hotelRoomDto) {
        try {
            return ResponseResult.returnSuccess(hotelRoomService.insert(hotelRoomDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

}
