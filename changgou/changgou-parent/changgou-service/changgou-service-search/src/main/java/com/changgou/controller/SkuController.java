package com.changgou.controller;

import com.changgou.service.SkuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author WEN
 * @Date 2020/12/16 22:27
 */
@RestController
@RequestMapping("/search")
@CrossOrigin // 支持跨域
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
     * 调用搜索实现
     * @param searchmMap  搜索条件
     * @return
     * @throws Exception
     */
    @GetMapping
    public Map search(@RequestParam(required = false) Map<String, String> searchmMap)throws Exception{
        return skuService.search(searchmMap);
    }

    /**
     * 数据导入
     * @return
     */
    @GetMapping("/import")
    public Result importData(){
        skuService.importData();
        return new Result(true, StatusCode.OK, "执行操作成功！");
    }
}
