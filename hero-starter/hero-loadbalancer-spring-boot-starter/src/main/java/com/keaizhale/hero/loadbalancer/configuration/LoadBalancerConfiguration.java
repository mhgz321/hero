package com.keaizhale.hero.loadbalancer.configuration;

import com.keaizhale.hero.loadbalancer.filter.LoadBalancerClientFilter;
import com.keaizhale.hero.loadbalancer.version.HeroLoadBalancer;
import com.keaizhale.hero.loadbalancer.version.VersionLoadBalancer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: T
 * date: 2023/3/24 15:19
 * author: keaizhale
 * version: 1.0
 */
@Configuration
@AutoConfigureBefore(GatewayReactiveLoadBalancerClientAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class LoadBalancerConfiguration {
    @Bean
    public HeroLoadBalancer grayLoadBalancer(DiscoveryClient discoveryClient) {
        return new VersionLoadBalancer(discoveryClient);
    }

    @Bean
    public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory, HeroLoadBalancer loadBalancer,
                                                                            GatewayLoadBalancerProperties properties) {
        return new LoadBalancerClientFilter(clientFactory, properties, loadBalancer);
    }
}
