package com.changgou.order.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 过期消息监听
 * @Author WEN
 * @Date 2021/4/12 20:34
 */
@Component
@RabbitListener(queues = "orderListenerQueue") // 此 orderListenerQueue 是配置类QueueConfig中的orderListenerQueue()方法名，保持一致
public class DelayMessageListener {

    /**
     * 延时队列监听
     * @param message
     */
    @RabbitHandler
    public void getDelayMessage(String message){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("监听消息的时间："+sdf.format(new Date()));
        System.out.println("监听到的消息："+message);
        // TODO 监听到消息后，取消订单，回滚库存
    }
}
