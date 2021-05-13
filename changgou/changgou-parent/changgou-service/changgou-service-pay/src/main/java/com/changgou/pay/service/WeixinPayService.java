package com.changgou.pay.service;

import java.util.Map;

/**
 * @Author WEN
 * @Date 2021/4/10 23:14
 */
public interface WeixinPayService {

    /**
     * 查询微信支付状态
     * @param outtradeno
     */
    Map  queryStatus(String outtradeno);

    /**
     * 创建二维码操作
     * @param parameterMap
     */
    Map createnative(Map<String,String> parameterMap);
}
