package com.changgou.token;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 令牌的创建和解析
 * @Author WEN
 * @Date 2021/1/15 21:28
 */
public class CreateJwtTestDemo {

    /**
     * 生成令牌
     */
    @Test
    public void testCreateToken() {
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
        System.out.println(token);
    }

    /**
     * 解析令牌
     */
    @Test
    public void testParseToken(){
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoic3oiLCJyb2xlIjoiYWRtaW4sdXNlciIsIm5pa2VuYW1lIjoidG9tY2F0In0.UtswJbDfasAhwN7CP4mHoWOA4XZLfZANFkUMhS0ClRior3mGXV06-4kTjq4JaZBWFGLS3Jc6gxhDmYSCYUuJ1ucUds_38LeahNlt8kd-6Vav4NBJVXCK6CsYyqUM3kA3Z3NmsK6wyKJspjV39gwRo2iLCnsq1PgAtlyxZ8EjJWZfrunwLxHOQFv94UcascsCOOYnO3wG5yWQCDpAN83X93JQeVAr2FKpMeJDjWb99UPYPoTg5LY524W5HNik8I73sleci6KJjpCqCBvscMmJaWhqxCYMsORt83ElZt3sO68i96nHrOwSZ8j6z-B5eNn_bdVp9oOOT304SkjcwacCUQ";
        String publick = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzQKFp1i9OwPSODI3t+Qz3nXlKVMvIMKQ4t6jGeL6CHkrYhJpfDdrBMy2jyqmEpMRpKvauY4uE4Yin+JtAzhtZRezKn/iwZPQNk+ktCLWFWBaEK3yyTCAEN2hZa3gRF4oFdqFzHOfnu/mB3Tz73KTRXVcIx3Px+LcOjoXw1h/xpGBN/GgxyBKpnVDkTQSpqx3MNbbmrq4RuaLhb+lmxLhy9XntaxtcFQaQ2LszsmYwGfTA9jywHDNakZVakN9+WIv1GR6cula/R8jr2yOR9JiYQK42FvZkkY2roXWMIBTQmwRRvJqk8sAAl7SpAAVatTSyYtFmLXQtF8dj8dIgKtX+wIDAQAB-----END PUBLIC KEY-----";

        Jwt jwt = JwtHelper.decodeAndVerify(token,
                new RsaVerifier(publick)
        );
        String claims = jwt.getClaims();
        System.out.println(claims);
    }


}
