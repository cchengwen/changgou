package com.changgou.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.changgou.seckill.service.SeckillOrderService;
import entity.SeckillStatus;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 监听秒杀下单拍支付延时（超时）队列
 * @Author WEN
 * @Date 2021/4/21 21:19
 */
@Component
@RabbitListener(queues = "seckillQueue")
public class DelaySeckillMessgaeListener {

    @Autowired
    private SeckillOrderService seckillOrderService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitHandler
    public void getMessage(String message) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("下单未支付过期回滚 时间：" + sdf.format(new Date()));
            // 获取用户的排队信息
            SeckillStatus seckillStatus = JSON.parseObject(message, SeckillStatus.class);

            // 如果此时Redis中没有用户的排队信息，则表明该订单已经处理，如果有用户排队信息，则表示用户尚未完成支付，关闭订单[要先关闭微信支付订单]
            Object userQueueStatus = redisTemplate.boundHashOps("UserQueueStatus").get(seckillStatus.getUsername());
            if (null != userQueueStatus){
                 // TODO 先关闭微信支付订单
                // 关闭订单
                seckillOrderService.deleteOrder(seckillStatus.getUsername());

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
