package com.suddenly.controller;

import com.suddenly.annotation.DisableLogin;
import com.suddenly.auth.LoginDto;
import com.suddenly.exception.CustomizeException;
import com.suddenly.responseResult.ResponseResult;
import com.suddenly.service.AutoLoginService;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AutoLoginController {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private AutoLoginService autoLoginService;

    @ApiOperation(value = "登录", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/login")
    @DisableLogin
    public ResponseResult<?> login(@RequestBody LoginDto loginDto) {
        if (StringUtils.isEmpty(loginDto.getAccount())) {
            return ResponseResult.returnFailure("登录账号不能为空");
        }
        if (StringUtils.isEmpty(loginDto.getPassword())) {
            return ResponseResult.returnFailure("登录密码不能为空");
        }
        if (StringUtils.isEmpty(loginDto.getVerCode())) {
            return ResponseResult.returnFailure("登录验证码不能为空");
        }
        try {
            return ResponseResult.returnSuccess(autoLoginService.login(loginDto));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "登出", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/logout/{token}")
    public ResponseResult<?> logout(@PathVariable("token") String token) {
        try {
            return ResponseResult.returnSuccess(autoLoginService.logout(token));
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

    @ApiOperation(value = "查询登录的账号", httpMethod = "POST", response = ResponseResult.class)
    @PostMapping("/query/account")
    public ResponseResult<List<String>> queryAccount() {
        try {
            return ResponseResult.returnSuccess(autoLoginService.queryAccount());
        } catch (CustomizeException e) {
            logger.error(e.getMessage());
            return ResponseResult.returnFailure(e.getResultEnum());
        }
    }

}
