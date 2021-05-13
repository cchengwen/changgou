package com.itheima.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 令牌的生成和解析
 * @Author WEN
 * @Date 2021/1/13 22:32
 */
public class JwtTest {

    /**
     * 创建令牌
     */
    @Test
    public void testCreateToken(){
        // 构建Jwt令牌的对象
        JwtBuilder builder = Jwts.builder();
        builder.setIssuer("文");
        builder.setIssuedAt(new Date()); // 颁发时间
        builder.setSubject("JWT令牌测试"); // 主题令牌
        builder.setExpiration(new Date(System.currentTimeMillis()+360000)); // 过期时间

        // 自定义载荷令牌
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("company", "智捷云");
        userInfo.put("address", "广西玉林");
        userInfo.put("money", 50000);
        builder.addClaims(userInfo);

        builder.signWith(SignatureAlgorithm.HS256, "itcast"); // 参数1：签名算法；参数2：秘钥(盐)
        String token = builder.compact();
        System.out.println("token令牌："+token);
    }


    /**
     * 令牌解析
     */
    @Test
    public void parseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiLmlociLCJpYXQiOjE2MTA1NDk5NzIsInN1YiI6IkpXVOS7pOeJjOa1i-ivlSIsImV4cCI6MTYxMDU1MDMzMiwiYWRkcmVzcyI6IuW5v-ilv-eOieaelyIsIm1vbmV5Ijo1MDAwMCwiY29tcGFueSI6IuaZuuaNt-S6kSJ9.wMP7VR6FekqdpFDLuduct_yZn_htFQWUIXsIK9kVubo";
        Claims claims = Jwts.parser()
                .setSigningKey("itcast") // 秘钥（盐）
                .parseClaimsJws(token) // 要解析的令牌对象
                .getBody();// 获取解析后的数据
        System.out.println(claims.toString());
    }



}
