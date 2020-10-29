package com.suddenly.auth;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultAuthCacheServiceProvider implements IAuthCacheFacade{


    /**
     * 当前登录用户缓存池，默认是map，后期可以根据要求扩展到Redis或其它缓存
     */
    private Map<String, LoginResultInfo> loginCache = new ConcurrentHashMap<>();

    private Map<String, String> stringCacheMap = new ConcurrentHashMap<>();


    @Override
    public void cacheLoginAccount(LoginResultInfo loginResultInfo) {
        loginCache.put(loginResultInfo.getToken(),loginResultInfo);

    }

    @Override
    public LoginResultInfo getCacheLoginAccount(String token) {
        return loginCache.get(token);
    }

    @Override
    public void clearCacheLogin(String token) {
        loginCache.remove(token);
    }

    @Override
    public void cacheStringValue(String key, String value) {
        stringCacheMap.put(key,value);
    }

    @Override
    public void clearStringValue(String key){
        stringCacheMap.remove(key);
    }

    @Override
    public String getValueByKey(String key) {
        return stringCacheMap.get(key);
    }

    @Override
    public Map<String, String> getAll() {
        Map<String, String> map = new HashMap<>();
        loginCache.entrySet().stream().forEach(a -> {
            map.put(a.getValue().getAccount(), a.getValue().getToken());
        });
        return map;
    }
}
