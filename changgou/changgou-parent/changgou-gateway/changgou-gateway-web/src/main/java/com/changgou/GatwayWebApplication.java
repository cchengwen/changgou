package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author WEN
 * @Date 2021/1/11 23:31
 */
@SpringBootApplication
@EnableEurekaClient
public class GatwayWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatwayWebApplication.class, args);
    }

    /**
     * 创建用户唯一标识，使用IP作为用户唯一标识，来根据IP进行限流操作
     */
    @Bean(name = "ipKeyPesolver")
    public KeyResolver userKeyPesolver(){
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
//                return Mono.just("");// 需要使用的用户身份识别唯一标识[IP]
                String ip = exchange.getRequest().getRemoteAddress().getHostString();
                System.out.println("用户请求的IP："+ip);
                return Mono.just(ip);
            }
        };
    }
}
