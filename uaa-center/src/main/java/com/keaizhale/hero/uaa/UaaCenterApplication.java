package com.keaizhale.hero.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * description: 认证中心
 * date: 2023/3/24 13:11
 * author: keaizhale
 * version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UaaCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UaaCenterApplication.class, args);
    }
}
