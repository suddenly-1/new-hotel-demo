package com.suddenly.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.suddenly.auth.*;
import com.suddenly.entity.UserInfo;
import com.suddenly.exception.CustomizeException;
import com.suddenly.responseResult.ResultEnum;
import com.suddenly.service.AutoLoginService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AutoLoginServiceImpl implements AutoLoginService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private IAuthCacheFacade authCacheFacade;

    @Override
    public LoginResultInfo login(LoginDto loginDto) throws CustomizeException {
        try {
            Map<String, String> map = authCacheFacade.getAll();
            if (map.containsKey(loginDto.getAccount())) {
                authCacheFacade.clearCacheLogin(map.get(loginDto.getAccount()));
            }
            LoginResultInfo loginResultInfo = new LoginResultInfo();
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginDto.getAccount(), loginDto.getPassword());
            subject.login(usernamePasswordToken);
            Object loginObj = subject.getPrincipal();
            if (loginObj != null) {
                UserInfo userInfo = (UserInfo) loginObj;
                loginResultInfo.setId(userInfo.getId());
                loginResultInfo.setAccount(userInfo.getAccountNumber());
                // JWT
                TokenPayload tokenPayload = new TokenPayload();
                tokenPayload.setSub(String.valueOf(loginResultInfo.getId()));
                String strPayload = JSONObject.toJSONString(tokenPayload);
                String token = JwtUtil.generateToken(strPayload);
                loginResultInfo.setToken(token);
                authCacheFacade.cacheLoginAccount(loginResultInfo);
            }
            return loginResultInfo;
        } catch (CustomizeException e) {
            logger.error("登录账号失败");
            throw new CustomizeException(e, ResultEnum.AUTH_ACCOUNT_LOGIN_ERROR);
        }
    }

    @Override
    public Boolean logout(String token) throws CustomizeException {
        try {
            authCacheFacade.clearCacheLogin(token);
            return true;
        } catch (CustomizeException e) {
            logger.error("登出账号失败");
            throw new CustomizeException(e, ResultEnum.AUTH_ACCOUNT_LOGOUT_ERROR);
        }
    }

    @Override
    public List<String> queryAccount() throws CustomizeException {
        try {
            List<String> accountList = authCacheFacade.getAll().entrySet().stream().map(a -> a.getKey()).collect(Collectors.toList());
            return accountList;
        } catch (CustomizeException e) {
            logger.error("查询登录账号失败");
            throw new CustomizeException(e, ResultEnum.QUERY_LOGIN_ACCOUNT_ERROR);
        }
    }

    @Override
    public LoginResultInfo getLoginAccountPermissions(String token) throws CustomizeException {
        try {
            LoginResultInfo cacheLoginAccount = authCacheFacade.getCacheLoginAccount(token);
            return cacheLoginAccount;
        } catch (Exception e) {
            logger.error("获取账号信息失败");
            throw new CustomizeException(e, ResultEnum.QUERY_ACCOUNT_INFO_FAILURE);
        }
    }


}
