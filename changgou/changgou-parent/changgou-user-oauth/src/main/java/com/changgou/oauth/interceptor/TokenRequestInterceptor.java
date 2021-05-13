package com.changgou.oauth.interceptor;

import com.changgou.oauth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @Author WEN
 * @Date 2021/1/16 23:36
 */
@Configuration
public class TokenRequestInterceptor implements RequestInterceptor {
    /**
     * Feign执行之前，进行拦截
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        /**
         * 从数据库加载查询用户信息
         *  遇到的问题：
         *      1、没有令牌，Feign调用之前，生成令牌（admin权限的令牌）
         *      2、Feign调用之前，令牌需要携带过去
         *      3、Feign调用之前，令牌需要存放到header文件中
         *      4、请求 -> Feign调用 -> 拦截哭RequestInterceptor -> Feign调用之前执行拦截
         */
        // 生成admin(管理员)令牌
        String token = AdminToken.adminToken();
        requestTemplate.header("Authorization", "bearer "+token);
    }
}
