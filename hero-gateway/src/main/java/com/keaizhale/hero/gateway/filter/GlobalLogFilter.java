package com.keaizhale.hero.gateway.filter;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalLogFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(GlobalLogFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Incoming Request: {} {}", exchange.getRequest().getMethod().name(), exchange.getRequest().getURI());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("Outgoing Response: {} {}", exchange.getResponse().getStatusCode().toString(), exchange.getResponse().getHeaders());
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
