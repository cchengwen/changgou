package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 微信支付启动类
 * @Author WEN
 * @Date 2021/1/24 23:12
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
public class WeixinPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeixinPayApplication.class, args);
    }
}
