package com.keaizhale.hero.loadbalancer.annotation;

import com.keaizhale.hero.loadbalancer.configuration.WebClientBalancerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务系统之间调用负载均衡开关
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({WebClientBalancerConfiguration.class})
public @interface EnableWebClientLoadBalancer {
}
