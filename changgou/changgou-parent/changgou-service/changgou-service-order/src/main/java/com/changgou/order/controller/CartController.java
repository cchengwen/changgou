package com.changgou.order.controller;

import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 购物车操作
 * @Author WEN
 * @Date 2021/1/16 22:13
 */
@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    /**
     * 购物车列表
     */
    @GetMapping("/list")
    public Result<List<OrderItem>> list(){
        // 用户的令牌信息 -> 解析令牌信息 -> username
//        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        String token = details.getTokenValue();
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        System.out.println("用户信息："+userInfo);
        String username = userInfo.get("username");

        // 获取用户登录名
//        String username = "szitheima";
        List<OrderItem> list = cartService.list(username);
        return new Result<List<OrderItem>>(true, StatusCode.OK, "购物车列表查询成功！", list);
    }

    /**
     * 加入购物车
     *   1：加入购物车的数量
     *   2：端口ID
     */
    @GetMapping("/add")
    public Result add(Integer num, Long id){
        // 获取用户令牌
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        System.out.println("用户信息："+userInfo);
        String username = userInfo.get("username");
        // 加入购物车
        cartService.add(num, id, username);
//        cartService.add(num, id, "szitheima");
        return new Result(true, StatusCode.OK, "加入购物车成功！");
    }


}
