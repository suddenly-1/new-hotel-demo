package com.suddenly.config;

import com.alibaba.fastjson.JSON;
import com.suddenly.annotation.DisableLogin;
import com.suddenly.auth.IAuthCacheFacade;
import com.suddenly.auth.LoginResultInfo;
import com.suddenly.responseResult.IResultEnum;
import com.suddenly.responseResult.ResponseResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Arrays;

import static com.suddenly.responseResult.ResultEnum.ACCOUNT_NOT_LOGIN;
import static com.suddenly.responseResult.ResultEnum.AUTH_ACCOUNT_NOT_LOGIN;

@Component
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LogManager.getLogger(getClass());

    @Resource
    private IAuthCacheFacade authCacheFacade;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果handle不属于方法，返回True
        logger.info("-----------请求路径:{},请求地址{}",request.getRequestURL(),request.getServletPath());
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Annotation[] annotations = handlerMethod.getMethod().getAnnotations();
        if (annotations != null) {
            // 增加了DisableLogin（表示无需通过登录），直接返回True
            if (Arrays.stream(annotations).filter(an -> an instanceof DisableLogin).findAny().isPresent()) {
                return super.preHandle(request, response, handler);
            }
            // 判断账号是否登录
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) { //未登录，直接返回错误信息
                return ResErrorOut(response, ACCOUNT_NOT_LOGIN);
            }
            // 获取账号信息
            LoginResultInfo cacheLoginAccount = authCacheFacade.getCacheLoginAccount(token);
            if (cacheLoginAccount == null) {
                return ResErrorOut(response, AUTH_ACCOUNT_NOT_LOGIN);
            }
        }
        return super.preHandle(request, response, handler);
    }


    private Boolean ResErrorOut(HttpServletResponse response, IResultEnum authAccountNotHasPermissions) {
        ResponseResult result = ResponseResult.returnFailure(authAccountNotHasPermissions);
        writeResponse(result, response); //返回登录对象
        return Boolean.FALSE;
    }

    private Boolean resErrorOut(HttpServletResponse response, Integer code, String msg){
        IResultEnum resultEnum = new IResultEnum() {
            @Override
            public Integer getCode() {
                return code;
            }
            @Override
            public String getMessage() {
                return msg;
            }
        };
        ResponseResult result = ResponseResult.returnFailure(resultEnum);
        writeResponse(result, response); //返回登录对象
        return Boolean.FALSE;
    }


    private void writeResponse(ResponseResult<?> result, HttpServletResponse response) {
        if (result == null) {
            return;
        }
        String resJson = JSON.toJSONString(result);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(resJson);
        } catch (IOException e) {
            logger.error("拦截器返回结果失败", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
