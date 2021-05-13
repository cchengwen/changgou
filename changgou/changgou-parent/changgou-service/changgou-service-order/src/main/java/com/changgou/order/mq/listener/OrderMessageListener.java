package com.changgou.order.mq.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 监听MQ
 * @Author WEN
 * @Date 2021/4/11 16:57
 */
@Component
@RabbitListener(queues = "${mq.pay.queue.order}") // 从配置文件中读取
public class OrderMessageListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void getMessage(String message) throws Exception{
        // 支付结果
        Map<String, String> resultMap = JSON.parseObject(message, Map.class);
        System.out.println("监听到的支付结果："+resultMap);

        // 通信标识 return_code
        String return_code = resultMap.get("return_code");
        if (return_code.equals("SUCCESS")){
            // 业务结果 result_code
            String result_code = resultMap.get("result_code");
            // 订单号 out_trade_no
            String out_trade_no = resultMap.get("out_trade_no");

            // 支付成功，修改订单状态
            if (result_code.equals("SUCCESS")){
                // 微信支付交易流水号 transaction_id
                orderService.updateStatus(out_trade_no, resultMap.get("time_end"), resultMap.get("transaction_id"));

            }else {
                // TODO
                // 关闭支付 -> 调用微信关闭支付接口：此时会有两情况：一是刚好用户已对支付成功，二是未支付
                // 如果是情况一：调用 orderService.updateStatus(out_trade_no, resultMap.get("time_end"), resultMap.get("transaction_id"));
                // 情况二：调用如下
                // 支付失败，关闭支付，取消订单，回滚库存
                orderService.deleteOrder(out_trade_no);
            }

        }


    }

}
