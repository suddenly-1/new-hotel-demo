package com.suddenly.controller;

import com.github.pagehelper.PageInfo;
import com.suddenly.entity.request.CreditPageDto;
import com.suddenly.entity.response.UserCreditVo;
import com.suddenly.exception.CustomizeException;
import com.suddenly.responseResult.ResponseResult;
import com.suddenly.service.UserCreditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "信用管理")
@RestController
@RequestMapping("/credit")
public class UserCreditController {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private UserCreditService userCreditService;

    @ApiOperation(value = "分页查询", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/query/page")
    public ResponseResult<PageInfo<UserCreditVo>> queryPage(@RequestBody CreditPageDto creditPageDto) {
        try {
            return ResponseResult.returnSuccess(userCreditService.queryPage(creditPageDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }


}
