package com.changgou.oauth.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author WEN
 * @Date 2021/1/16 23:46
 */
public class AdminToken {

    /**
     * 管理员令牌发放
     * @return
     */
    public static String adminToken(){
        // 加载证书，读取类路径中的文件
        ClassPathResource resource = new ClassPathResource("changgou68.jks");

        // 读取证书数据，加载读取证书中的数据，参数说明：参数1：读取的文件，参数2：生成证书时的密码
        KeyStoreKeyFactory keyStoreKey = new KeyStoreKeyFactory(resource, "changgou68".toCharArray());

        //获取证书中的一对秘钥，参数说明：参数1：生成证书时的别名，参数2：生成证书时的密码
        KeyPair keyPair = keyStoreKey.getKeyPair("changgou68", "changgou68".toCharArray());

        // 获取私钥 -> RSA算法
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 创建令牌，需要私钥加起[RSA算法]
        Map<String, Object> payload = new HashMap<>();
        payload.put("nikename", "tomcat");
        payload.put("address", "sz");
        payload.put("authorities", new String[]{"admin", "oauth"});
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(payload), new RsaSigner(privateKey));
        // 获取令牌数据
        String token = jwt.getEncoded();
        return token;
    }
}
