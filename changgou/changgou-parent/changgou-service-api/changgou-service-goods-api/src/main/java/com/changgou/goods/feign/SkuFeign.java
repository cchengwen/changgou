package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import entity.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author WEN
 * @Date 2020/12/16 22:06
 */
@FeignClient(value = "goods") // 告诉它，要调用哪个微服务，value = "goods"：与配置文件中的goods微服务名称一致
@RequestMapping("/sku")
public interface SkuFeign {

    /**
     * 商品信息递减
     * @param decrmap
     * @return
     */
    @GetMapping("/decr/count")
    Result decrCount(@RequestParam Map<String, Integer> decrmap);

    /***
     * 根据ID查询Sku数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Sku根据ID查询",notes = "根据ID查询Sku方法详情",tags = {"SkuController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    Result<Sku> findById(@PathVariable Long id);

    /**
     * 查询Sku全部数据
     * @return
     */
    @GetMapping
    Result<List<Sku>> findAll();
}
