package com.keaizhale.hero.loadbalancer.filter;

import com.keaizhale.hero.loadbalancer.version.HeroLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * description: 负载均衡过滤器
 * 实现通过版本号进行负载均衡
 * date: 2023/3/22 10:24
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
public class LoadBalancerClientFilter extends ReactiveLoadBalancerClientFilter {
    private HeroLoadBalancer loadBalancer;
    private GatewayLoadBalancerProperties properties;

    public LoadBalancerClientFilter(LoadBalancerClientFactory clientFactory,
                                    GatewayLoadBalancerProperties properties,
                                    HeroLoadBalancer loadBalancer) {
        super(clientFactory, properties);
        this.properties = properties;
        this.loadBalancer = loadBalancer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
        if (url == null
                || (!"lb".equals(url.getScheme()) && !"lb".equals(schemePrefix))) {
            return chain.filter(exchange);
        }

        ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);

        return choose(exchange).doOnNext((response) -> {
            if (!response.hasServer()) {
                throw NotFoundException.create(this.properties.isUse404(), "Unable to find instance for " + url.getHost());
            } else {
                ServiceInstance retrievedInstance = response.getServer();
                URI uri = exchange.getRequest().getURI();
                String overrideScheme = retrievedInstance.isSecure() ? "https" : "http";
                if (schemePrefix != null) {
                    overrideScheme = url.getScheme();
                }

                DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(retrievedInstance, overrideScheme);
                URI requestUrl = this.reconstructURI(serviceInstance, uri);
                if (log.isTraceEnabled()) {
                    log.trace("LoadBalancerClientFilter url chosen: " + requestUrl);
                }

                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR, response);
            }
        }).then(chain.filter(exchange));
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        ServiceInstance serviceInstance = loadBalancer.choose(uri.getHost(), exchange.getRequest());
        return Mono.just(new DefaultResponse(serviceInstance));
    }

    @Override
    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER;
    }

}
