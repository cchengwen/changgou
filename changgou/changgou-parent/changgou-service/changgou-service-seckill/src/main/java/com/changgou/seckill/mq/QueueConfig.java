package com.changgou.seckill.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 秒杀订单延时队列
 *  1、延时（超时）队列 -> 负责数据暂时存储 Queue1
 *  2、真正监听的消息队列 Queue2
 *  3、创建交换机
 * @Author WEN
 * @Date 2021/4/21 23:02
 */
@Configuration
public class QueueConfig {

    /**
     * 延时（超时）队列 -> 负责数据暂时存储 Queue1
     * @return
     */
    @Bean
    public Queue delaySeckillQueue(){
        return QueueBuilder.durable("delaySeckillQueue")
                .withArgument("x-dead-letter-exchange", "seckillExchange")
                .withArgument("x-dead-letter-routing-key", "seckillQueue")
                .build();
    }
    /**
     * 真正监听的消息队列 Queue2
     */
    @Bean
    public Queue seckillQueue(){
        return new Queue("seckillQueue", true);
    }


    /**
     * 创建交换机
     */
    @Bean
    public Exchange seckillListenerExchange(){
        return new DirectExchange("seckillListenerExchange");
    }


    /**
     * seckillQueue 邦定 交换机
     * @param seckillQueue
     * @param seckillListenerExchange
     * @return
     */
    @Bean
    public Binding seckillListenerBinding(Queue seckillQueue, Exchange seckillListenerExchange){
        return BindingBuilder.bind(seckillQueue).to(seckillListenerExchange).with("seckillQueue").noargs();
    }
}
