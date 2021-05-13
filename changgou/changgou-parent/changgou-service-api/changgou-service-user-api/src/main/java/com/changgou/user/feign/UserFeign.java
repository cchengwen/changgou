package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author WEN
 * @Date 2021/1/16 15:10
 */
@FeignClient(value = "user")
@RequestMapping(value = "/user")
public interface UserFeign {

    /**
     * 添加用户积分
     * @param points
     * @return
     */
    @GetMapping("/points/add")
    Result addPoints(@RequestParam("points") Integer points);

    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
//    @GetMapping({"/{id}", "/load/{id}"})
    @GetMapping({"/load/{id}"})
    Result<User> findById(@PathVariable String id);
}
