package com.changgou.oauth.controller;

import com.changgou.oauth.service.UserLoginService;
import com.changgou.oauth.util.AuthToken;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author WEN
 * @Date 2021/1/15 23:05
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {
    // 客户ID
    @Value("${auth.clientId}")
    private String clientId;
    // 客户端秘钥
    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Autowired
    private UserLoginService userLoginService;

    /**
     * 登录方法
     *  参数传递：
     *      1、账号： username=szitheima
     *      2、密码： password=szitheima
     *      3、授权方式： grant_type=password
     *  请求头传递
     *      4、Basic Base64(客户端ID:客户端秘钥)    Basic Y2hhbmdnb3U6Y2hhbmdnb3U=
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
    public Result login(String username, String password)throws Exception{
        String grant_type = "password";
        AuthToken authToken = userLoginService.login(username, password, clientId, clientSecret, grant_type);
        if (authToken != null){
            return new Result(true, StatusCode.OK, "登录成功！", authToken);
        }
        return new Result(false, StatusCode.LOGINERROR, "登录失败！");
    }

}
