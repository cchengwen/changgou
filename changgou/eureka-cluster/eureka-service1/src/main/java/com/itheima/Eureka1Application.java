package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author WEN
 * @Date 2021/4/28 20:32
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka1Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka1Application.class, args);
    }
}
