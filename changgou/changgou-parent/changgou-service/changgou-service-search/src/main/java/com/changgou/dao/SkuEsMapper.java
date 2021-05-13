package com.changgou.dao;

import com.changgou.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author WEN
 * @Date 2020/12/16 22:18
 */
//@Repository // 此注解可加可不加
public interface SkuEsMapper extends ElasticsearchRepository<SkuInfo, Long> {
}
