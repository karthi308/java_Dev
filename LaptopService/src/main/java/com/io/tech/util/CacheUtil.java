package com.io.tech.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CacheUtil {
    private static RedisTemplate<String, String> redisTemplate;

    public CacheUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static void addObjectToCache(String key, String object, long ttl_in_minutes) {
        try {
            if (null != object) {
                redisTemplate.opsForValue().set(key, object);
                redisTemplate.expire(key, ttl_in_minutes, TimeUnit.MINUTES);
            }
        } catch (Exception e) {
            log.error("Error occurred in cacheUtil addObjectToCache : " + e.getMessage());
        }
    }

    public static String getObjectFromCache(String key) {
        String object = null;
        try {
            if (null != key) {
                object = redisTemplate.opsForValue().get(key);
            }
        } catch (Exception e) {
            log.error("Error occurred in cacheUtil getObjectFromCache : " + e.getMessage());
        }
        return object;
    }


}