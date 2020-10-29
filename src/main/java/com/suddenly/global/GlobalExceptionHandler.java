package com.suddenly.global;

import com.suddenly.exception.CustomizeException;
import com.suddenly.responseResult.IResultEnum;
import com.suddenly.responseResult.ResponseResult;
import com.suddenly.responseResult.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    @ExceptionHandler(Throwable.class)
    public ResponseResult handleThrowable(Throwable e, HttpServletRequest request) {
        log.error("URL:{} ,系统异常: ",request.getRequestURI(), e);
        return ResponseResult.returnFailure(e.getMessage(), ResultEnum.SYSTEM_EXCEPTION);
    }

    @ExceptionHandler(BindException.class)
    public ResponseResult exceptionHandler(BindException e, HttpServletRequest request) {
        String failMsg = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("URL:{} ,绑定异常:{} ", request.getRequestURI(),failMsg);
        return ResponseResult.returnFailure(Arrays.asList(e.getMessage(), failMsg), ResultEnum.BIND_EXCEPTION);
    }

}
