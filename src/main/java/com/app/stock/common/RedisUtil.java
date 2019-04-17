package com.app.stock.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis对应的操作类
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }
    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }
    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        Object result = null;
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        if(result==null){
            return null;
        }
        return result.toString();
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime,TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 插入map记录(key-value)
     * @param key
     * @param name
     * @param obj
     */
    public void addMap(String key, String name, Object obj){
        String val = null;
        if(obj instanceof String){
            val = obj.toString();
        } else {
            val = new Gson().toJson(obj);
        }
        redisTemplate.opsForHash().put(key,name,val);
    }

    /**
     * 根据key和属性name删除map记录
     * @param key
     * @param name
     */
    public void deleteMap(String key,String name){
        if(redisTemplate.opsForHash().get(key,name) != null){
            redisTemplate.opsForHash().delete(key,name);
        }
    }

    /**
     * 根据key查找map记录
     * @param key
     * @param name
     * @return
     */
    public  String getMap(String key,String name){
        String result = null;
        if(redisTemplate.opsForHash().get(key,name) != null){
            result = redisTemplate.opsForHash().get(key,name).toString();
        }
        return result;
    }
}
