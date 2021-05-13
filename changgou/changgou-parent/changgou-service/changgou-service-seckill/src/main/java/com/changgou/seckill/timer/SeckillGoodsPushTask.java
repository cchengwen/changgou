package com.changgou.seckill.timer;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import entity.DateUtil;
import entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 定时将秒杀商品存入到redis缓存
 * @Author WEN
 * @Date 2021/4/12 23:23
 */
@Component
public class SeckillGoodsPushTask {
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 定时操作
     * 解释：
     *      0/5 ：是从0开始执行，间隔5秒执行一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void loadGoodsPushRedis(){
        /**
         * 1、查询符合当前参与秒杀的时间菜单
         * 2、秒杀商品库存 > 0 stock_count
         * 3、审核状态 -> 审核通过 status = 1
         * 4、开始时间 start_time，结束时间 end_time
         *      时间菜单的开始时间 =< start_time && end_time < 时间菜单的开始时间+2小时
         */
        // 求时间菜单
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date dateMenu : dateMenus) {
            // 时间的字符串格式 yyyyMMddHH
            String timespace ="SeckillGoods_"+ DateUtil.data2str(dateMenu, "yyyyMMddHH");
            /**
             * 1、审核状态 -> 审核通过 status = 1
             * 2、秒杀商品库存 > 0 stock_count
             * 3、开始时间 start_time，结束时间 end_time
             *      时间菜单的开始时间 =< start_time && end_time < 时间菜单的开始时间+2小时
             */
            Example example = new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();
            // 审核状态 -> 审核通过 status = 1
            criteria.andEqualTo("status", "1");
            // 秒杀商品库存 > 0 stock_count
            criteria.andGreaterThan("stockCount", 0);
            // 时间菜单的开始时间 =< start_time && end_time < 时间菜单的开始时间+2小时
            criteria.andGreaterThanOrEqualTo("startTime", dateMenu);
            criteria.andLessThan("endTime", DateUtil.addDateHour(dateMenu, 2));
            /**
             * 排除已经存入到redis中的SeckillGoods
             *  1、求出当前命名空间下所有的商品ID（key）
             *  2、每次查询之前排除掉之前存在的商品的key的数据
             */
            Set keys = redisTemplate.boundHashOps(timespace).keys();
            if (null != keys && keys.size() > 0){
                criteria.andNotIn("id", keys);
            }

            // 执行查询
            List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectByExample(example);
            for (SeckillGoods seckillGood : seckillGoods) {
                System.out.println("商品ID："+seckillGood.getId()+" ----->>>"+timespace);
                // 存入redis
                redisTemplate.boundHashOps(timespace).put(seckillGood.getId(), seckillGood);

                // 给每个商品做个队列
                redisTemplate.boundListOps("SeckillGoodsCountList_"+seckillGood.getId()).leftPushAll(putAllIds(seckillGood.getStockCount(), seckillGood.getId()));
            }
        }
    }

    /**
     * 获取每个商品ID的集合
     * @param num
     * @param id 此id仅作为个数，标记有多少个
     * @return
     */
    public Long[] putAllIds(Integer num, Long id){
        Long[] ids = new Long[num];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = id;
        }
        return ids;
    }
}
