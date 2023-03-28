package com.keaizhale.hero.file.configuration;

import com.keaizhale.hero.common.exception.ServiceException;
import com.keaizhale.hero.file.api.FileOpenApi;
import com.keaizhale.hero.loadbalancer.annotation.EnableWebClientLoadBalancer;
import com.keaizhale.hero.loadbalancer.webclient.BalancerExchangeFilterFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

/**
 * description: 初始化webclient，用于调用远程服务
 * date: 2023/3/24 13:21
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
@Configuration
@EnableWebClientLoadBalancer
public class FileApiConfigurationg {
    @Autowired
    private BalancerExchangeFilterFunction reactorLoadBalancerExchangeFilterFunction;

    /**
     * @title: 创建FileClientApi
     * @description: 创建文件中心API客户端，采用负载均衡实现
     * @author: keaizhale
     * @dateTime: 2023/3/24 14:48
     * @return com.keaizhale.uaa.api.FileClientApi
     */
    @Bean
    public FileOpenApi fileOpenApiLoadBalancer() {
        WebClient client = WebClient.builder().filter(reactorLoadBalancerExchangeFilterFunction)
                .baseUrl("lb://file-center").filter(renewTokenFilter()).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(FileOpenApi.class);
    }

    /**
     * @title: 处理响应结果
     * @description: TODO
     * @author: keaizhale
     * @dateTime: 2023/3/24 14:47
     * @return org.springframework.web.reactive.function.client.ExchangeFilterFunction
     */
    public ExchangeFilterFunction renewTokenFilter() {
        return (request, next) -> next.exchange(request).flatMap(response -> {
            if (!response.statusCode().is2xxSuccessful()) {
                log.error("调用文件中心服务异常，响应码：{}，URL: {}", response.statusCode(), request.url());
                return Mono.error(new ServiceException("服务访问异常"));
            } else {
                return Mono.just(response);
            }
        });
    }

}
