package com.suddenly.auth;

import java.util.Map;

/**
 * 登录用户缓存功能
 */
public interface IAuthCacheFacade {

    /**
     * 缓存登录用户
     * @param loginResultInfo
     */
    void cacheLoginAccount(LoginResultInfo loginResultInfo) ;

    /**
     * 从缓存中获得已经登录的账号
     * @param token
     */
    LoginResultInfo getCacheLoginAccount(String token) ;


    /**
     * 清除缓存用户
     * @param token
     */
    void clearCacheLogin(String token) ;

    /**
     * 缓存String类型键值对
     * @param key
     * @param value
     */
    void cacheStringValue(String key, String value)  ;

    void clearStringValue(String key) ;

    /**
     * 根据key获取value
     *
     * @param key
     * @return
     */
    String getValueByKey(String key);

    /**
     * 查询所有缓存的账号
     */
    Map<String, String> getAll();

}
