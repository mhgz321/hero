package com.keaizhale.hero.satoken.configuration;

//import cn.dev33.satoken.filter.SaServletFilter;
//import cn.dev33.satoken.interceptor.SaInterceptor;
//import cn.dev33.satoken.same.SaSameUtil;
//import cn.dev33.satoken.util.SaResult;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * description: SecurityConfiguration
 * date: 2023/4/4 15:17
 * author: keaizhale
 * version: 1.0
 */
//public class SecurityConfiguration implements WebMvcConfigurer {
//    /**
//     * 注册sa-token拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册路由拦截器，自定义验证规则
//        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
//    }
//
//    /**
//     * sa-token过滤器
//     */
//    @Bean
//    public SaServletFilter getSaServletFilter() {
//        return new SaServletFilter()
//                .addInclude("/**")
//                .addExclude("/actuator/**")
//                .setAuth(obj -> SaSameUtil.checkCurrentRequestToken())
//                .setError(e -> SaResult.error("认证失败，禁止访问系统资源").setCode(HttpStatus.UNAUTHORIZED.value()));
//    }
//}
