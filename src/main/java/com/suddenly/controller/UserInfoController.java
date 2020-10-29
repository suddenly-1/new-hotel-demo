package com.suddenly.controller;

import com.github.pagehelper.PageInfo;
import com.suddenly.base.PageDto;
import com.suddenly.entity.request.UserInfoDto;
import com.suddenly.entity.response.UserInfoVo;
import com.suddenly.exception.CustomizeException;
import com.suddenly.responseResult.ResponseResult;
import com.suddenly.service.UserInfoService;
import io.swagger.annotations.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserInfoController {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "查询", httpMethod = "GET", response = ResponseResult.class)
    @GetMapping("/query")
    public ResponseResult<UserInfoVo> query(@RequestParam Long id) {
        try {
            return ResponseResult.returnSuccess(userInfoService.queryById(id));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "查询Vo", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/queryVo")
    public ResponseResult<List<UserInfoVo>> queryVo(@RequestBody UserInfoDto userInfoDto) {
        try {
            return ResponseResult.returnSuccess(userInfoService.queryVo(userInfoDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "分页查询", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/query/page")
    public ResponseResult<PageInfo<UserInfoVo>> queryPage(@RequestBody PageDto pageDto){
        try {
            return ResponseResult.returnSuccess(userInfoService.queryPage(pageDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "新增", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/add")
    public ResponseResult<?> add(@Valid @RequestBody UserInfoDto userInfoDto) {
        try {
            userInfoService.addUserInfo(userInfoDto);
            return ResponseResult.returnSuccess();
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "修改", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/update")
    public ResponseResult<?> update(@RequestBody UserInfoDto userInfoDto) {
        try {
            userInfoService.updateUserInfo(userInfoDto);
            return ResponseResult.returnSuccess();
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "删除", httpMethod = "GET", response = ResponseResult.class)
    @GetMapping("/delete")
    public ResponseResult<?> delete(@RequestParam Long id) {
        try {
            userInfoService.deleteById(id);
            return ResponseResult.returnSuccess();
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "信用充值", httpMethod = "GET", response = ResponseResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id"),
            @ApiImplicitParam(name = "money", value = "充值金额")
    })
    @GetMapping("/recharge")
    public ResponseResult<?> rechargeCredit(@RequestParam Long userId, @RequestParam Double money) {
        try {
            return ResponseResult.returnSuccess(userInfoService.rechargeCredit(userId, money));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

}

