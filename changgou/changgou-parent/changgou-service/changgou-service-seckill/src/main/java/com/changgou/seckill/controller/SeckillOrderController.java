package com.changgou.seckill.controller;

import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.service.SeckillOrderService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.SeckillStatus;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/seckillOrder")
@CrossOrigin
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @GetMapping("/query")
    public Result queryStatus(){
        String username = "szitheima";
        SeckillStatus seckillStatus = seckillOrderService.queryStatus(username);
        if (null != seckillStatus) {
            return new Result(true, StatusCode.OK, "查询状态成功！", seckillStatus);
        }
        return new Result(false, StatusCode.NOTFOUNDERROR, "抢单失败！");

    }

    /**
     * 添加秒杀订单
     * @param time
     * @param id
     * 用户的登录名字
     */
    @GetMapping("/add")
    public Result add(String time, long id){
        String username = "szitheima";

        seckillOrderService.add(time, id, username);

        return new Result(true, StatusCode.OK, "正在排队...！");
    }
}
