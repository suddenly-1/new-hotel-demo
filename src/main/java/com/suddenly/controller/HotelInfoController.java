package com.suddenly.controller;

import com.suddenly.base.PageDto;
import com.suddenly.entity.request.HotelDto;
import com.suddenly.entity.request.HotelPageDto;
import com.suddenly.entity.request.HotelVoPageDto;
import com.suddenly.exception.CustomizeException;
import com.suddenly.responseResult.ResponseResult;
import com.suddenly.service.HotelInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "酒店信息")
@RestController
@RequestMapping("/hotel")
public class HotelInfoController {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private HotelInfoService hotelInfoService;

    @ApiOperation(value = "分页查询", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/query/page")
    public ResponseResult<?> query(@RequestBody HotelPageDto hotelPageDto) {
        try {
            return ResponseResult.returnSuccess(hotelInfoService.queryPage(hotelPageDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "查询酒店详情", httpMethod = "GET", response = ResponseResult.class)
    @GetMapping("/query/details")
    public ResponseResult<?> queryDetails(@RequestParam Long id) {
        try {
            return ResponseResult.returnSuccess(hotelInfoService.queryDetails(id));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "查询预定过的酒店", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/query/booked")
    public ResponseResult<?> queryBookedHotel(@RequestBody PageDto pageDto) {
        try {
            return ResponseResult.returnSuccess(hotelInfoService.queryBookedHotel(pageDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "维护酒店信息", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/update/info")
    public ResponseResult<?> updateHotelInfo(@RequestBody HotelDto hotelDto) {
        try {
            return ResponseResult.returnSuccess(hotelInfoService.updateHotel(hotelDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "查询酒店是否存在工作人员", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/query/is-exist")
    public ResponseResult<?> queryHotel(@RequestBody HotelVoPageDto hotelVoPageDto) {
        try {
            return ResponseResult.returnSuccess(hotelInfoService.queryHotel(hotelVoPageDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

}
