package com.keaizhale.hero.loadbalancer.webclient;

import com.keaizhale.hero.loadbalancer.version.HeroLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerClientRequestTransformer;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * description: BalancerExchangeFilterFunction
 * date: 2023/3/24 16:03
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
public class BalancerExchangeFilterFunction extends ReactorLoadBalancerExchangeFilterFunction {
    private HeroLoadBalancer loadBalancer;
    public BalancerExchangeFilterFunction(ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerFactory, List<LoadBalancerClientRequestTransformer> transformers, HeroLoadBalancer loadBalancer) {
        super(loadBalancerFactory, transformers);
        this.loadBalancer = loadBalancer;
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction next) {
        return super.filter(clientRequest, next);
    }

    @Override
    protected Mono<Response<ServiceInstance>> choose(String serviceId, Request<RequestDataContext> request) {
        ServiceInstance serviceInstance = loadBalancer.choose(serviceId, request.getContext().getClientRequest().getHeaders().getFirst(HeroLoadBalancer.HEADER_VERSION_KEY));
        return Mono.just(new DefaultResponse(serviceInstance));
    }
}
