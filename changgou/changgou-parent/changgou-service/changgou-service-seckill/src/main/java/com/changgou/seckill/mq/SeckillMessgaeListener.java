package com.changgou.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.changgou.seckill.service.SeckillOrderService;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 秒杀mq监听
 * @Author WEN
 * @Date 2021/4/21 21:19
 */
@Component
/**
 * 此监听从微信支付类(WeixinPayController)发送过来的数据
 * WeixinPayController -> createNative(@RequestParam Map<String, String> paramterMap):数据发开始发起 -> nofityurl(HttpServletRequest request)：微信传回来，此方法接收后发到mq
 */
@RabbitListener(queues = "${mq.pay.queue.eckillorder}")
public class SeckillMessgaeListener {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @RabbitHandler
    public void getMessage(String message) {
        System.out.println("秒杀监听：" + message);
        try {
            // 将支付信息转成Map
            Map<String, String> resultMap = JSON.parseObject(message, Map.class);
            // return_code -> 通信标识 -> SUCCESS
            String return_code = resultMap.get("return_code");
            // out_trade_no -> 订单号
            String out_trade_no = resultMap.get("out_trade_no");
            // 自定义数据
            String attach = resultMap.get("attach");
            Map<String, String> attachMap = JSON.parseObject(attach, Map.class);
            String username = attachMap.get("username");

            if ("SUCCESS".equals(return_code)) {
                // result_code -> 业务结果 -> SUCCESS -> 改订单状态
                String result_code = resultMap.get("result_code");
                if ("SUCCESS".equals(result_code)) {
                    // 改订单状态
                    seckillOrderService.updatePayStatus(username, resultMap.get("transaction_id"), resultMap.get("time_end"));
                    // 清理用户排队信息
                }else {
                    //                           FAIL   ->  删除订单[真实工作中存入到Mysql]  -> 回滚库存
                    seckillOrderService.deleteOrder(username);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
