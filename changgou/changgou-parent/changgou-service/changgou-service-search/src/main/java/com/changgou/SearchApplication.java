package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @Author WEN
 * @Date 2020/12/14 21:06
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 排斥禁用数据库加载
@EnableEurekaClient
@EnableFeignClients("com.changgou.goods.feign") // Skufeign类所在的包路径
@EnableElasticsearchRepositories(basePackages = "com.changgou.dao")
public class SearchApplication {

    public static void main(String[] args) {
        /**
         * <p>注：如果出现冲突，就把下面一句开放出来，如果不报错，则不用</p>
         * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错，
         * 解决netty冲突后初始化client时还会抛出异常
         * availableProcessors is already set to [12], rejecting [12]
         */
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SearchApplication.class, args);
    }
}
