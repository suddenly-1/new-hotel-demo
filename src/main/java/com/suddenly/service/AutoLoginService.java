package com.suddenly.service;

import com.suddenly.auth.LoginDto;
import com.suddenly.auth.LoginResultInfo;
import com.suddenly.exception.CustomizeException;

import javax.security.auth.message.AuthException;
import java.util.List;

public interface AutoLoginService {

    /**
     * 登录
     * @param loginDto
     * @return LoginResultInfo
     * @throws CustomizeException
     */
    LoginResultInfo login(LoginDto loginDto) throws CustomizeException;

    /**
     * 登出
     * @param token
     * @return Boolean
     * @throws CustomizeException
     */
    Boolean logout(String token) throws CustomizeException;

    /**
     * 查询登录的账号
     */
    List<String> queryAccount() throws CustomizeException;

    /**
     * 根据授权token获得用户的权限列表
     * @param token
     * @return
     * @throws CustomizeException
     */
    LoginResultInfo getLoginAccountPermissions(String token) throws CustomizeException;


}
