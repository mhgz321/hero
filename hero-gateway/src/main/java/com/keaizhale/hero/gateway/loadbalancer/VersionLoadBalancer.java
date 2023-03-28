//package com.keaizhale.hero.gateway.loadbalancer;
//
//import com.keaizhale.hero.gateway.filter.GlobalLogFilter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//public class VersionLoadBalancer implements HeroLoadBalancer {
//    private DiscoveryClient discoveryClient;
//
//    public VersionLoadBalancer(DiscoveryClient discoveryClient) {
//        this.discoveryClient = discoveryClient;
//    }
//
//    private static final Logger log = LoggerFactory.getLogger(GlobalLogFilter.class);
//    private static final String HEADER_VERSION_KEY = "x-version";
//
//    @Override
//    public ServiceInstance choose(String serviceId, ServerHttpRequest request) {
//        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
//
//        //注册中心无实例 抛出异常
//        if (instances == null || instances.size() == 0) {
//            log.warn("访问的服务不存在： {}", serviceId);
//            throw new RuntimeException("访问的服务不存在 " + serviceId);
//        }
//        if (instances.size() == 1) {
//            log.debug("访问的服务：{}, 只有一个实例，直接返回该实例", serviceId);
//            return instances.get(0);
//        }
//
//        // 获取请求 version
//        String version = request.getHeaders().getFirst(HEADER_VERSION_KEY);
//
//        if (StringUtils.hasLength(version)) {
//            for (ServiceInstance instance : instances) {
//                Map<String, String> metadata = instance.getMetadata();
//                if (version.equalsIgnoreCase(metadata.get(HEADER_VERSION_KEY))) {
//                    log.debug("根据版本号路由成功， 参数：{} 实例：{}", version, instance);
//                    return instance;
//                }
//            }
//        }
//        //当匹配不到时，直接随机返回
//        Random random = new Random();
//        return instances.get(random.nextInt(instances.size()));
//    }
//}
