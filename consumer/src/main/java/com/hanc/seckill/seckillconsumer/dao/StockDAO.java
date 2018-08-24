package com.hanc.seckill.seckillconsumer.dao;

import com.hanc.seckill.seckillconsumer.entity.Stock;

public interface StockDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateStockByOptimistic(Stock record);

    int updateByPrimaryKey(Stock record);
}