package com.suddenly.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

public class RedisAuthCacheServiceProvider implements IAuthCacheFacade{

    @Autowired
    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void cacheLoginAccount(LoginResultInfo loginResultInfo) {
        String token = loginResultInfo.getToken();
        redisTemplate.opsForValue().set(token, loginResultInfo);
    }

    @Override
    public LoginResultInfo getCacheLoginAccount(String token) {
        Object res = redisTemplate.opsForValue().get(token);
        return (LoginResultInfo) res;
    }

    @Override
    public void clearCacheLogin(String token) {

    }

    @Override
    public void cacheStringValue(String key, String value) {

    }

    @Override
    public void clearStringValue(String key) {

    }

    @Override
    public String getValueByKey(String key) {
        return null;
    }

    @Override
    public Map<String, String> getAll() {
        return null;
    }
}
