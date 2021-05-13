package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author WEN
 * @Date 2021/4/28 23:17
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka4Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka4Application.class, args);
    }
}
