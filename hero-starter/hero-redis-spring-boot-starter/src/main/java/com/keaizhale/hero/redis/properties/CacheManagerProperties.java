package com.keaizhale.hero.redis.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * description: CacheManagerProperties
 * date: 2023/4/7 14:11
 * author: keaizhale
 * version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "hero.cache")
public class CacheManagerProperties {
    private List<CacheConfig> configs;

    @Data
    public static class CacheConfig {
        /**
         * cache key
         */
        private String key;
        /**
         * 过期时间，sec
         */
        private long second = 60;
    }
}
