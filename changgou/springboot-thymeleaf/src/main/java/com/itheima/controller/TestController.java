package com.itheima.controller;

import com.itheima.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @Author WEN
 * @Date 2021/1/10 11:54
 */
@Controller
@RequestMapping("/test")
public class TestController {

    /**
     * 基本案例
     * @param model
     * @return
     */
    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("message", "hello thymeleaf!");

        // 创建一个List<User>，将List<User> 存入到Mode中，到页面使用Thymeleaf标签显示
        List<User> users = new ArrayList<>();
        users.add(new User(1, "张三", "深圳"));
        users.add(new User(2, "张四", "武汉"));
        users.add(new User(3, "张五", "开明"));
        users.add(new User(4, "张六", "新疆"));
        users.add(new User(5, "张七", "广西"));
        model.addAttribute("users", users);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("No", "123");
        dataMap.put("Address", "广西");
        model.addAttribute("dataMap", dataMap);

        // 日期
        model.addAttribute("date", new Date());
        // if条件判断
        model.addAttribute("age", 22);
        return "demo1";
    }
}
