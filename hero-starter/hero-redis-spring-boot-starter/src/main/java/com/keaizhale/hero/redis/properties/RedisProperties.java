package com.keaizhale.hero.redis.properties;

import com.keaizhale.hero.redis.constant.ModelEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description: RedisProperties
 * date: 2023/4/7 9:40
 * author: keaizhale
 * version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "hero.redis")
public class RedisProperties {
    /** redisson模式，默认单例*/
    private ModelEnum model= ModelEnum.SINGLE;

    /** 线程池数量,默认值 = 当前处理核数量 * 2 */
    private int threads;

    /** Netty线程池数量,默认值 = 当前处理核数量 * 2 */
    private int nettyThreads;

    /** redisson单机模式配置 */
    private SingleServerConfig singleServerConfig;

    @Data
    public static class SingleServerConfig {

        /** 客户端名称 */
        private String clientName;
        private String address;
        private Integer subscriptionConnectionMinimumIdleSize = 1;
        private Integer subscriptionConnectionPoolSize = 50;
        private Integer connectionMinimumIdleSize = 32;
        private Integer connectionPoolSize = 64;
        private Integer database = 0;
        private Long dnsMonitoringInterval = 5000L;
    }

}
