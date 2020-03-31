package com.xinxin.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
//    @LoadBalanced //RestTemplate客户端通过服务名调用eureka server注册中心的服务 达到负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
