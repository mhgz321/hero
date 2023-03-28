package com.keaizhale.hero.gateway;

import com.keaizhale.hero.loadbalancer.annotation.EnableGatewayLoadBalancer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableGatewayLoadBalancer
public class HeroGatewayApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(HeroGatewayApplication.class, args);
	}

}
