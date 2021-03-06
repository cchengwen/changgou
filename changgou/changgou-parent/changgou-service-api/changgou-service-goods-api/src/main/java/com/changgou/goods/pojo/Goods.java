package com.changgou.goods.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 商家信息组合对象
 *      List<Sku>
 *      Spu
 * @Author WEN
 * @Date 2020/11/25 21:41
 */
public class Goods implements Serializable {
    // Spu信息
    private Spu spu;
    // Sku集合信息
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
