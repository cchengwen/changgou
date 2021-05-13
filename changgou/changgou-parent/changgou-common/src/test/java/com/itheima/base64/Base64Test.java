package com.itheima.base64;

import org.junit.Test;

import java.util.Base64;

/**
 * Base64加密、解密
 * @Author WEN
 * @Date 2021/1/13 22:13
 */
public class Base64Test {

    /**
     * 加密
     * @throws Exception
     */
    @Test
    public void testEncode() throws Exception{
        byte[] encode = Base64.getEncoder().encode("abcdefg".getBytes());
        String encodeStr = new String(encode, "UTF-8");
        System.out.println("加密后的密文："+encodeStr);
    }

    @Test
    public void testDecode() throws Exception{
        String str = "YWJjZGVmZw=="; // 加密后的密文
        byte[] decode = Base64.getDecoder().decode(str.getBytes());
        String decodeStr = new String(decode, "UTF-8");
        System.out.println("解密后的明文："+decodeStr);
    }


}
