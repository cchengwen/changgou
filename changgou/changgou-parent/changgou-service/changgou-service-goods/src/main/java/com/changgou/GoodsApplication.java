package com.changgou;

import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableEurekaClient // 开启eureka客户端
@MapperScan(basePackages = {"com.changgou.goods.dao"}) // 开启通过Mapper的包扫描
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }


    /**
     * IdWorker 生成器
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0,0);
    }
}
