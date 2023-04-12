package com.keaizhale.hero.satoken.configuration;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.SaTokenDaoDefaultImpl;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpInterfaceDefaultImpl;
import cn.dev33.satoken.stp.StpLogic;
import com.keaizhale.hero.satoken.dao.SaTokenDaoImpl;
import com.keaizhale.hero.satoken.interceptor.SaInterceptorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * description: SaTokenConfiguration
 * date: 2023/4/4 14:55
 * author: keaizhale
 * version: 1.0
 */
@Configuration
public class SaTokenConfiguration {
    /**
     *  Sa-Token 权限认证，默认JWT简单实现类
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

    /**
     *  权限认证接口，可以自定义实现
     */
    @Bean
    public StpInterface stpInterface() {
        return new SaInterceptorImpl();
    }

    /**
     * 持久层，可以自定义实现
     */
    @Bean
    public SaTokenDao saTokenDao() {
        return new SaTokenDaoDefaultImpl();
    }

    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary() {
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("Authorization");             // token名称 (同时也是cookie名称)
        config.setTimeout(30 * 24 * 60 * 60);       // token有效期，单位s 默认30天
        config.setActivityTimeout(-1);              // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setIsConcurrent(true);               // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsShare(true);                    // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
        config.setIsReadCookie(false);
        config.setIsReadHeader(true);
        config.setIsReadBody(true);
        config.setTokenStyle("simple-uuid");               // token风格
        config.setTokenPrefix("Bearer"); //token前缀
        config.setJwtSecretKey("dsfdsafdsafdsfewfewf");
        config.setIsLog(true);                     // 是否输出操作日志
        return config;
    }
}
