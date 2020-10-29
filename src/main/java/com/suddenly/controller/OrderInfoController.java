package com.suddenly.controller;

import com.github.pagehelper.PageInfo;
import com.suddenly.entity.request.CurrentPageDto;
import com.suddenly.entity.request.EvaluationDto;
import com.suddenly.entity.request.OrderInfoDto;
import com.suddenly.entity.request.OrderPageDto;
import com.suddenly.entity.response.OrderInfoVo;
import com.suddenly.exception.CustomizeException;
import com.suddenly.responseResult.ResponseResult;
import com.suddenly.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(tags = "订单管理")
@RestController
@RequestMapping("/order")
public class OrderInfoController {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation(value = "查询订单", httpMethod = "GET", response = ResponseResult.class)
    @ApiImplicitParam(name = "orderNumber", value = "订单号")
    @GetMapping("/query")
    public ResponseResult<OrderInfoVo> query(@RequestParam String orderNumber) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.queryByOrderNumber(orderNumber));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "分页查询", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/query/page")
    public ResponseResult<PageInfo<OrderInfoVo>> queryPage(@RequestBody OrderPageDto orderPageDto) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.queryPage(orderPageDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "生成订单", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/create")
    public ResponseResult<Boolean> create(@RequestBody OrderInfoDto orderInfoDto) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.create(orderInfoDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "撤销订单", httpMethod = "GET", response = ResponseResult.class)
    @GetMapping("/revoke")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNumber", value = "订单号"),
            @ApiImplicitParam(name = "status", value = "状态")
    })
    public ResponseResult<Boolean> revoke(@RequestParam String orderNumber, @RequestParam Integer status) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.revoke(orderNumber, status));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "执行订单", httpMethod = "GET", response = ResponseResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNumber", value = "订单号"),
            @ApiImplicitParam(name = "status", value = "状态")
    })
    @GetMapping("/carry-out")
    public ResponseResult<Boolean> carriedOut(@RequestParam String orderNumber, @RequestParam Integer status) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.carriedOut(orderNumber, status));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "执行异常订单", httpMethod = "GET", response = ResponseResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNumber", value = "订单号"),
            @ApiImplicitParam(name = "status", value = "状态")
    })
    @GetMapping("/carry-out/exception")
    public ResponseResult<Boolean> carryOutExceptionOrder(@RequestParam String orderNumber, @RequestParam Integer status) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.carryOutExceptionOrder(orderNumber, status));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "查询订单数量", httpMethod = "GET", response = ResponseResult.class)
    @GetMapping("/query/count")
    public ResponseResult<Map<String, Object>> queryOrderCount(@RequestParam Long userId, @RequestParam Long hotelId, @RequestParam String status) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.queryOrderCount(userId, hotelId, status));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "查询用户在当前酒店的订单", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/query/current")
    public ResponseResult<PageInfo<OrderInfoVo>> queryCurrentOrder(@RequestBody CurrentPageDto currentPageDto) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.queryCurrentOrder(currentPageDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "评价", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/evaluation")
    public ResponseResult<Boolean> evaluation(@RequestBody EvaluationDto evaluationDto) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.evaluation(evaluationDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "完成订单", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/carry-out")
    public ResponseResult<Boolean> carryOutOrder(String orderNumber, Integer status) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.carryOutOrder(orderNumber, status));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "查询当天订单", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/query/that-day")
    public ResponseResult<?> queryThatDay(@RequestBody OrderPageDto orderPageDto) {
        try {
            return ResponseResult.returnSuccess(orderInfoService.queryThatDay(orderPageDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }



}
