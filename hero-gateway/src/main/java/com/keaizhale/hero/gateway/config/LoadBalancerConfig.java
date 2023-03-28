//package com.keaizhale.hero.gateway.config;
//
//import com.keaizhale.hero.gateway.filter.LoadBalancerClientFilter;
//import com.keaizhale.hero.gateway.loadbalancer.HeroLoadBalancer;
//import com.keaizhale.hero.gateway.loadbalancer.VersionLoadBalancer;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
//import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
//import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
//import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@AutoConfigureBefore(GatewayReactiveLoadBalancerClientAutoConfiguration.class)
//@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
//public class LoadBalancerConfig {
//    @Bean
//    public HeroLoadBalancer grayLoadBalancer(DiscoveryClient discoveryClient) {
//        return new VersionLoadBalancer(discoveryClient);
//    }
//
//    @Bean
//    public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory, HeroLoadBalancer loadBalancer,
//                                                                            GatewayLoadBalancerProperties properties) {
//        return new LoadBalancerClientFilter(clientFactory, properties, loadBalancer);
//    }
//}
