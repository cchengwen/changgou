package com.changgou;

import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author WEN
 * @Date 2021/4/12 23:07
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = {"com.changgou.seckill.dao"})
@EnableScheduling // 开启定时任务
@EnableAsync // 开启线程
public class SeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
