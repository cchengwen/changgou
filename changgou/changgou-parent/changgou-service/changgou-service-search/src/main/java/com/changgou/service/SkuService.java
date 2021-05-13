package com.changgou.service;

import java.util.Map;

/**
 * @Author WEN
 * @Date 2020/12/16 22:14
 */
public interface SkuService {

    /**
     * 条件搜索
     * @param searchMap
     * @return
     */
    Map<String, Object> search(Map<String, String> searchMap) throws Exception;

    /**
     * 导入数据到索引库中
     */
    void importData();
}
