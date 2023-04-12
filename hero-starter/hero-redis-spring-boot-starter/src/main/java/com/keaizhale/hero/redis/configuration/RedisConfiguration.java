package com.keaizhale.hero.redis.configuration;

import com.alibaba.fastjson2.JSONObject;
import com.keaizhale.hero.redis.constant.ModelEnum;
import com.keaizhale.hero.redis.properties.CacheManagerProperties;
import com.keaizhale.hero.redis.properties.RedisProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * description: RedisConfiguration
 * date: 2023/4/7 9:39
 * author: keaizhale
 * version: 1.0
 */
@Configuration
@EnableConfigurationProperties(value = {RedisProperties.class})
public class RedisConfiguration {
    @Autowired
    private RedisProperties redisProperties;

    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        Config config=new Config();
        config.setThreads(redisProperties.getThreads())
                .setNettyThreads(redisProperties.getNettyThreads())
                .setCodec(new FastJson2Codec());
        switch (redisProperties.getModel()) {
            case SINGLE:
                RedisProperties.SingleServerConfig serverConfig = redisProperties.getSingleServerConfig();
                config.useSingleServer()
                        .setClientName(serverConfig.getClientName())
                        .setAddress(serverConfig.getAddress())
                        .setConnectionMinimumIdleSize(serverConfig.getConnectionMinimumIdleSize())
                        .setSubscriptionConnectionPoolSize(serverConfig.getSubscriptionConnectionPoolSize())
                        .setConnectionMinimumIdleSize(serverConfig.getConnectionMinimumIdleSize())
                        .setConnectionPoolSize(serverConfig.getConnectionPoolSize())
                        .setDatabase(serverConfig.getDatabase())
                        .setDnsMonitoringInterval(serverConfig.getDnsMonitoringInterval());
            break;
        }

        return Redisson.create(config);

    }

}
