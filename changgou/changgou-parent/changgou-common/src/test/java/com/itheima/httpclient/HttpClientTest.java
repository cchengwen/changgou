package com.itheima.httpclient;

import entity.HttpClient;
import org.junit.Test;

import java.io.IOException;

/**
 * HttpClient 使用案例
 * @Author WEN
 * @Date 2021/1/24 15:12
 */
public class HttpClientTest {

    /**
     * 发送HttpClient请求
     *      发送指定参数
     *      可以获取响应的结果
     */
    @Test
    public void testHttpClient() throws IOException {
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        // 创建HttpClient对象
        HttpClient httpClient = new HttpClient(url);
        // 要发送的xml数据
        String xml = "<xml><name>张三</name></xml>";
        // 设置请求的xml参数
        httpClient.setXmlParam(xml);
        // 请求头协议  http  |  https
        httpClient.setHttps(true);
        // 发送请求  post请求
        httpClient.post();
        // 获取响应数据
        String result = httpClient.getContent();
        System.out.println("响应结果：\n"+result);

    }
}
