package com.changgou;

import entity.FeignInterceptor;
import entity.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author WEN
 * @Date 2021/1/16 18:00
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.changgou.goods.feign","com.changgou.user.feign"})
@MapperScan(basePackages = "com.changgou.order.dao")
public class OrderApplication {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /**
     * 将Feign调用拦截器注入到容器中
     * @return
     */
    @Bean
    public FeignInterceptor feignInterceptor(){
        logger.info("OrderApplication >> feignInterceptor 将Feign调用拦截器注入到容器中");
        return new FeignInterceptor();
    }

    /**
     * 创建IdWorker
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0, 0);
    }
}
