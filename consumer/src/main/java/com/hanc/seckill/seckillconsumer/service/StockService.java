package com.hanc.seckill.seckillconsumer.service;

import com.hanc.seckill.seckillconsumer.entity.Stock;

/**
 * @author: cuill
 * @date: 2018/8/17 11:29
 */
public interface StockService {


    void updateStockOptimisticByRedis(Stock stock);

    void updateMessageToRedis(int id);
}
