package com.changgou.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author WEN
 * @Date 2021/1/10 13:25
 */
@FeignClient(name = "search")
@RequestMapping(value = "/search")
public interface SkuFeign {

    /**
     * 调用搜索实现
     * @param searchmMap  搜索条件
     * @return
     * @throws Exception
     */
    @GetMapping
    Map search(@RequestParam(required = false) Map<String, String> searchmMap)throws Exception;
}
