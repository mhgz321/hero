package com.keaizhale.hero.loadbalancer.configuration;

import com.keaizhale.hero.loadbalancer.version.HeroLoadBalancer;
import com.keaizhale.hero.loadbalancer.version.VersionLoadBalancer;
import com.keaizhale.hero.loadbalancer.webclient.BalancerExchangeFilterFunction;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerClientRequestTransformer;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * description: T
 * date: 2023/3/24 15:19
 * author: keaizhale
 * version: 1.0
 */
@Configuration
public class WebClientBalancerConfiguration {
    @Bean
    public HeroLoadBalancer grayLoadBalancer(DiscoveryClient discoveryClient) {
        return new VersionLoadBalancer(discoveryClient);
    }

    @Bean
    public BalancerExchangeFilterFunction nalancerExchangeFilterFunction(ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerFactory,
                                                                         List<LoadBalancerClientRequestTransformer> transformers,
                                                                         HeroLoadBalancer loadBalancer) {
        return new BalancerExchangeFilterFunction(loadBalancerFactory, transformers, loadBalancer);
    }
}
