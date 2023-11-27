package com.laptop.code.laptop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CacheUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean setValue(String key, String object, int ttl_in_minutes) {
        boolean cacheStatus = false;
        try {
            if (null != object && getConnection()) {
                redisTemplate.opsForValue().set(key, object);
                cacheStatus = true;
                redisTemplate.expire(key, ttl_in_minutes, TimeUnit.HOURS);
            }
        } catch (Exception e) {
            log.error("Error occured in CacheUtil Set Method : ", e.getMessage());
        }
        return cacheStatus;
    }

    public String getValue(String key) {
        String value = null;
        try {
            if (null != key) {
                value = redisTemplate.opsForValue().get(key);
            }
        } catch (Exception e) {
            log.error("Error occurred in CacheUtil getValue : ", e.getMessage());
        }
        return value;
    }

    private boolean getConnection() {
        boolean connectionStatus = false;
        try {
            RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
            if (redisConnection != null) {
                connectionStatus = true;
            }
        } catch (Exception e) {
            log.error("Error Occurred in CacheUtil getConnection : ", e.getMessage());
        }
        return connectionStatus;

    }

//    public String getObjectFromCache(int tokenId, String exchange, String service_name) {
//        String key = CommonUtil.getKey(tokenId, exchange, service_name);
//        String obj = (String) getValue(key);
//        return obj;
//    }
//
//    public void addObjectToCache(int tokenId, String exchange, String object, String service_name,
//                                 byte ttl_in_minutes) {
//        if (object != null) {
//            String key = CommonUtil.getKey(tokenId, exchange, service_name);
//            setValue(key, object, ttl_in_minutes);
//        }
//    }
}
