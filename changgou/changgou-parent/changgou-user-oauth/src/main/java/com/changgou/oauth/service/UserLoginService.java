package com.changgou.oauth.service;

import com.changgou.oauth.util.AuthToken;

/**
 * @Author WEN
 * @Date 2021/1/15 23:15
 */
public interface UserLoginService {
    /**
     * 登录操作：密码模式
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @param grant_type
     */
    AuthToken login(String username, String password, String clientId, String clientSecret, String grant_type)throws Exception;
}
