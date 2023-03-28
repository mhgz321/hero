package com.keaizhale.hero.loadbalancer.version;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.server.reactive.ServerHttpRequest;

public interface HeroLoadBalancer {
    String HEADER_VERSION_KEY = "x-version";

    ServiceInstance choose(String serviceId, ServerHttpRequest request);

    ServiceInstance choose(String serviceId, String version);
}
