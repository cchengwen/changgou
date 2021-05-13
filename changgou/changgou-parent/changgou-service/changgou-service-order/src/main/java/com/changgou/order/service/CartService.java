package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

/**
 * @Author WEN
 * @Date 2021/1/16 22:15
 */
public interface CartService {


    /**
     * 购物车集合查询
     * @param username 用户名
     * @return
     */
    List<OrderItem> list(String username);

    /**
     * 加入购物车实现
     * @param num 加入购物车的数量
     * @param id SkuID
     * @param username 用户名称
     */
    void add(Integer num, Long id, String username);

}
