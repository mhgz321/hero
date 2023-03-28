package com.keaizhale.hero.loadbalancer.annotation;

import com.keaizhale.hero.loadbalancer.configuration.LoadBalancerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 网关负载均衡开关
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({LoadBalancerConfiguration.class})
public @interface EnableGatewayLoadBalancer {
}
