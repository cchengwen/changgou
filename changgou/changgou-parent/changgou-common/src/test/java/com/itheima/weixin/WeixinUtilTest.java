package com.itheima.weixin;

import com.github.wxpay.sdk.WXPayUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信SDK相关测试
 * @Author WEN
 * @Date 2021/1/24 14:48
 */
public class WeixinUtilTest {

    /**
     * 1、生成随机字符
     * 2、将Map转成Xml字符串
     * 3、将Map转成Xml字符串，并且生成签名
     * 4、将Xml字符串转成Map
     *
     */
    @Test
    public void testDemo() throws Exception {
        // 随机字符串
        String str = WXPayUtil.generateNonceStr();
        System.out.println("随机字符串："+str);

        // 将Map转成Xml字符串
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("id", "No.001");
        dataMap.put("title", "畅购商城手机支付");
        dataMap.put("money", "888");
        String xmlStr = WXPayUtil.mapToXml(dataMap);
        System.out.println("xml字符串：\n"+xmlStr);

        // 将Map转成Xml字符串，并且生成签名
        String signerXmlStr = WXPayUtil.generateSignedXml(dataMap, "itcast");
        System.out.println("xml字符串，并带有签名：\n"+signerXmlStr);

        // 将Xml字符串转成Map
        Map<String, String> mapResult = WXPayUtil.xmlToMap(signerXmlStr);
        System.out.println("将Xml字符串转成Map：\n"+mapResult);

    }
}
