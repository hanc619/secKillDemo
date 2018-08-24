package com.hanc.seckill.seckillproducer.dao;

import com.hanc.seckill.seckillproducer.entity.StockOrder;

public interface StockOrderDAO {

    int deleteByPrimaryKey(Integer id);

    int insert(StockOrder record);

    int insertSelective(StockOrder record);

    StockOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockOrder record);

    int updateStatusByPrimaryKey(Integer id);

    int updateByPrimaryKey(StockOrder record);
}