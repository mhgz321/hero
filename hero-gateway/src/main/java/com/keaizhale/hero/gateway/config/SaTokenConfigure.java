package com.keaizhale.hero.gateway.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.keaizhale.hero.gateway.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: SaTokenConfigure
 * date: 2023/4/6 9:36
 * author: keaizhale
 * version: 1.0
 */
@Configuration
@EnableConfigurationProperties({SecurityProperties.class})
public class SaTokenConfigure {
    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaReactorFilter getSaReactorFilter(SecurityProperties securityProperties) {
        return new SaReactorFilter()
                // 指定 [拦截路由]
                .addInclude("/**")    /* 拦截所有path */
                // 指定 [放行路由]
                .addExclude("/favicon.ico")
                // 指定[认证函数]: 每次请求执行
                .setAuth(obj -> {
                    System.out.println("---------- sa全局认证");
                    // SaRouter.match("/test/test", () -> StpUtil.checkLogin());
                    SaRouter.match("/**")
                            .notMatch(securityProperties.getIgnores())
                            .check(() -> StpUtil.checkLogin());
                })
                // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数
                .setError(e -> {
                    System.out.println("---------- sa全局异常 ");
                    return SaResult.error(e.getMessage());
                })
                ;
    }
}
