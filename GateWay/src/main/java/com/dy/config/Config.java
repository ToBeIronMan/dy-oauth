package com.dy.config;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    //不采用负载均衡
//@Bean
//@LoadBalanced
//public RestTemplate restTemplate(RestTemplateBuilder builder){
//return builder.build();
//}
@LoadBalanced
@Bean
@Primary
RestTemplate loadBalanced() {
    return new RestTemplate();
}

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
