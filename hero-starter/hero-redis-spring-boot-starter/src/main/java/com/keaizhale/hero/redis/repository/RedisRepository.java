package com.keaizhale.hero.redis.repository;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import java.util.concurrent.TimeUnit;

/**
 * description: RedisRepository
 * date: 2023/4/7 11:05
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
public record RedisRepository(RedissonClient redissonClient) {
//    private RedissonClient redissonClient;

    /**
     * 获取 RedissonClient对象
     */
    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    /**
     * 设置有效时间(单位秒)
     * @param key redisKey
     * @param timeout 过期时间 秒
     * @return true
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     * @param key redisKey
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     * @return true
     */
    public boolean expire(final String key, final long timeout, final  TimeUnit timeUnit) {
        RBucket rBucket = redissonClient.getBucket(key);
        return rBucket.expire(timeout, timeUnit);
    }

    /**
     * 获取值
     * @param key
     * @return 泛型值
     */
    public <T> T getValue(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    /**
     * 设置值
     * @param key 缓存key
     * @param value 值
     * @param time 缓存时间（单位秒），-1永久
     */
    public <T> void setValue(String key, T value, Long time) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        if (time == null || time == -1) {
            bucket.set(value);
        } else {
            bucket.set(value, time, TimeUnit.SECONDS);
        }
    }

}
