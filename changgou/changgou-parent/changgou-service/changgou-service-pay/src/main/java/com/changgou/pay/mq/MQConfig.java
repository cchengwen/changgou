package com.changgou.pay.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * QM配置类
 * @Author WEN
 * @Date 2021/4/11 16:23
 */
@Configuration
public class MQConfig {

    /**
     * 读取配置文件中的信息的对象
     */
    @Autowired
    private Environment ent;

    /**
     * 创建队列
     */
    @Bean
    public Queue orderQueue(){
        return new Queue(ent.getProperty("mq.pay.queue.order"));
    }


    /**
     * 创建交换机
     */
    @Bean
    public Exchange orderExchange(){
        return new DirectExchange(ent.getProperty("mq.pay.exchange.order"), true, false);
    }


    /**
     * 队列绑定交换机
     */
    @Bean
    public Binding orderQueueExchange(Queue orderQueue, Exchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(ent.getProperty("mq.pay.routing.key")).noargs();
    }


    /***************************** 秒杀队列创建 start***************************************/

    /**
     * 创建队列
     */
    @Bean
    public Queue orderSeckillQueue(){
        return new Queue(ent.getProperty("mq.pay.queue.seckillorder"));
    }


    /**
     * 创建交换机
     */
    @Bean
    public Exchange orderSeckillExchange(){
        return new DirectExchange(ent.getProperty("mq.pay.exchange.seckillorder"), true, false);
    }


    /**
     * 队列绑定交换机
     */
    @Bean
    public Binding orderSeckillQueueExchange(Queue orderSeckillQueue, Exchange orderSeckillExchange){
        return BindingBuilder.bind(orderSeckillQueue).to(orderSeckillExchange).with(ent.getProperty("mq.pay.routing.seckillkey")).noargs();
    }
}
