package com.changgou.order.mq.queue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQ延时队列配置
 * @Author WEN
 * @Date 2021/4/12 19:45
 */
@Configuration
public class QueueConfig {

    /**
     * 创建Queue1，延时列表，会过期，过期后将数据发给Queue2
     */
    @Bean
    public Queue orderDelayQueue(){
        return QueueBuilder.durable("orderDelayQueue")
                .withArgument("x-dead-letter-exchange", "orderListenerExchange") // orderDelayQueue队列信息会过期，过期之后，进入到死信队列，死信队列数据绑定到其他交换机中（因为orderDelayQueue()的交换机会过期，因此数据会发送到此交换机中）
                .withArgument("x-dead-letter-routing-key", "orderListenerQueue") // 死信信息将路由到orderListenerQueue()中
                .build();
    }

    /**
     * 创建Queue2
     */
    @Bean
    public Queue orderListenerQueue(){
        return new Queue("orderListenerQueue", true);
    }

    /**
     * 创建交换机
     */
    @Bean
    public Exchange orderListenerExchange(){
        return new DirectExchange("orderListenerExchange");
    }

    /**
     * 队列Queue2绑定Exchange
     */
    @Bean
    public Binding orderListenerBinding(Queue orderListenerQueue, Exchange orderListenerExchange){
        return BindingBuilder.bind(orderListenerQueue).to(orderListenerExchange).with("orderListenerQueue").noargs();
    }
}
