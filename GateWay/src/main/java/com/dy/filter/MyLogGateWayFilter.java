package com.dy.filter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther zzyy
 * @create 2020-02-21 16:40
 */
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered
{
    @Autowired(required = false) // 这个可以解决idea报错
    @Qualifier("restTemplate") // 表示根据名称来找bean
    private RestTemplate restTemplate;
    private  String tokenCheck="http://127.0.0.1:8087/oauth/check_token?token={token}";

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {

        log.info("***********come in MyLogGateWayFilter:  "+new Date());

        String uname = exchange.getRequest().getQueryParams().getFirst("id");
        String token=exchange.getRequest().getHeaders().getFirst("Authorization");
        String url= null;
        url=exchange.getRequest().getURI().toURL().toString();
        log.info("iiiii"+url);
        System.out.println("获取ddd"+url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token",token);
        System.out.println(token);
        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);
        Map ops=new HashMap<>();
        try {
           ops= restTemplate.exchange(tokenCheck, HttpMethod.GET, httpEntity, Map.class,token).getBody();
        } catch (RestClientException e) {
            e.getRootCause();
            System.out.println("***********1爱上一片名你**************");
            System.out.println(e.getCause());
            System.out.println("***********2爱上一片名你**************");
            System.out.println(e.getRootCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String errorKey = "error";
        System.out.println(ops);
        if (ops.containsKey(errorKey)) {
            if (log.isDebugEnabled()) {
                log.debug("user info returned error: " + ops.get("error"));
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ops.get("error").toString());
        }
        if(uname == null)
        {
            log.info("*******用户名为null，非法用户，o(╥﹏╥)o");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder()
    {
        return 0;
    }
}
