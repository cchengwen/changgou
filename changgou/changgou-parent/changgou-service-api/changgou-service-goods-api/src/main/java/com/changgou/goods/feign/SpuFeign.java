package com.changgou.goods.feign;

import com.changgou.goods.pojo.Spu;
import entity.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author WEN
 * @Date 2021/1/16 22:31
 */
@FeignClient(value = "goods")
@RequestMapping("/spu")
public interface SpuFeign {

    /***
     * 根据ID查询Spu数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Spu根据ID查询",notes = "根据ID查询Spu方法详情",tags = {"SpuController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    Result<Spu> findById(@PathVariable Long id);
}
