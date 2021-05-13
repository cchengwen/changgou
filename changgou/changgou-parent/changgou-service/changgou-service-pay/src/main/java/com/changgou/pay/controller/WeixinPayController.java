package com.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * @Author WEN
 * @Date 2021/4/11 0:01
 */
@RestController
@RequestMapping(value = "/weixin/pay")
public class WeixinPayController {
    @Autowired
    private WeixinPayService weixinPayService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 支付结果通知回调方法
     * @param request
     * @return
     */
    @RequestMapping(value = "/notify/url")
    public String nofityurl(HttpServletRequest request)throws Exception{
        // 获取网络输入流
        ServletInputStream is = request.getInputStream();
        // 以前做法：创建一个OutputStream -> 输入文件中
        // 现在不需要写入到文件中，做法如下，创建一个
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 缓存区
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1){
            baos.write(buffer, 0, len);
        }

        // 微信支付结果的字节数组
        byte[] bytes = baos.toByteArray();
        String xmlresult = new String(bytes, "UTF-8");
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlresult);
        System.out.println(resultMap);
        // 获取自定义数据
        String attach = resultMap.get("attach");
        Map<String, String> map = JSON.parseObject(attach, Map.class);

        // 支付结果发给MQ
        rabbitTemplate.convertAndSend(map.get("exchange"), map.get("routingkey"), JSON.toJSONString(resultMap));
//        rabbitTemplate.convertAndSend("exchange.order", "queue.order", JSON.toJSONString(resultMap));

        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        return result;
    }

    /**
     * 微信支付状态查询
     * @param outtradeno
     * @return
     */
    @GetMapping("/status/query")
    public Result queryStatus(String outtradeno){
        Map map = weixinPayService.queryStatus(outtradeno);
        return new Result(true, StatusCode.OK, "查询微信支付状态成功！", map);
    }

    /**
     * 创建支付二维码
     * 普通订单：
     *      exchange：exchange.order
     *      routingkey：queue.order
     * 秒杀订单：
     *     exchange：exchange.seckillorder
     *     routingkey：queue.seckillorder
     *
     * @param paramterMap
     * @return
     */
    @GetMapping("/create/native")
    public Result createNative(@RequestParam Map<String, String> paramterMap){
        // 创建二维码
        Map createnative = weixinPayService.createnative(paramterMap);
        return new Result(true, StatusCode.OK, "创建二维码预付订单成功！", createnative);
    }
}
