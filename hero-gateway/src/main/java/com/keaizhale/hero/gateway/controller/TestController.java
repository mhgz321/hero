package com.keaizhale.hero.gateway.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public Object test(ServerHttpRequest reqeust) {
        String s = reqeust.getHeaders().getFirst("Authorization");
        System.out.println(s);
        return s;
    }
}
