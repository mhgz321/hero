package com.keaizhale.hero.gateway.filter;

import cn.dev33.satoken.same.SaSameUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * description: 过滤器会为 Request 请求头追加 Same-Token 参数，这个参数会被转发到子服务
 * https://sa-token.cc/doc.html#/micro/same-token
 * date: 2023/4/6 10:31
 * author: keaizhale
 * version: 1.0
 */
@Component
public class ForwardAuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest newRequest = exchange
                .getRequest()
                .mutate()
                // 为请求追加 Same-Token 参数
                .header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken())
                .build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
