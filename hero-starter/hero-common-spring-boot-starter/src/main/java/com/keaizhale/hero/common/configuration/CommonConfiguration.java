package com.keaizhale.hero.common.configuration;

import com.keaizhale.hero.common.bean.CommonConstant;
import com.keaizhale.hero.common.filter.CustomLocaleChangeInterceptor;
import com.keaizhale.hero.common.message.ServiceErrorFactory;
import com.keaizhale.hero.common.properties.CommonProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * description: CommonConfiguration
 * date: 2023/3/23 11:09
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CommonProperties.class})
public class CommonConfiguration implements WebMvcConfigurer {
    @Resource
    private CommonProperties commonProperties;
    @Bean
    public ServiceErrorFactory serviceErrorFactory() {
        return new ServiceErrorFactory(commonProperties.getI18ErrorPath());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return sessionLocaleResolver;
    }

    /**
     * locale change interceptor.
     *
     * @return LocaleChangeInterceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new CustomLocaleChangeInterceptor();
        interceptor.setParamName(CommonConstant.locale);
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(CommonConstant.locale);
        registry.addInterceptor(localeChangeInterceptor());
    }
}
