package com.hanc.seckill.seckillconsumer.service.impl;

import com.hanc.seckill.seckillconsumer.dao.StockDAO;
import com.hanc.seckill.seckillconsumer.entity.Stock;
import com.hanc.seckill.seckillconsumer.exception.ApiException;
import com.hanc.seckill.seckillconsumer.exception.EC;
import com.hanc.seckill.seckillconsumer.service.StockService;
import com.hanc.seckill.seckillconsumer.util.redis.RedisConstant;
import com.hanc.seckill.seckillconsumer.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: cuill
 * @date: 2018/8/17 11:29
 */
@Service
public class StockServiceImpl implements StockService {

    @Resource(name = "redisUtil")
    private RedisUtil redisUtil;

    @Autowired
    private StockDAO stockDAO;

    @Override
    public void updateStockOptimisticByRedis(Stock stock) {
        int count = stockDAO.updateStockByOptimistic(stock);
        if (count == 0) {
            throw new ApiException(EC.FAILED_BUSINESS.getCode(), "并发更新库存失败");
        }
        int sid = stock.getId();
        //将redis中的版本号和已售值进行更新
        redisUtil.hincrby(RedisConstant.REDIS_KEY_STOCK_SALE, String.valueOf(sid), 1);
        redisUtil.hincrby(RedisConstant.REDIS_KEY_STOCK_VERSION, String.valueOf(sid), 1);
    }

    @Override
    public void updateMessageToRedis(int id) {
        Stock stock = stockDAO.selectByPrimaryKey(id);
        String idStr = String.valueOf(id);
        if (!redisUtil.hexists(RedisConstant.REDIS_KEY_STOCK_COUNT, idStr)) {
            redisUtil.hincrby(RedisConstant.REDIS_KEY_STOCK_COUNT, idStr, stock.getCount());
        }
        if (!redisUtil.hexists(RedisConstant.REDIS_KEY_STOCK_SALE, idStr)) {
            redisUtil.hincrby(RedisConstant.REDIS_KEY_STOCK_SALE, idStr, stock.getSale());
        }
        if (!redisUtil.hexists(RedisConstant.REDIS_KEY_STOCK_VERSION, idStr)) {
            redisUtil.hincrby(RedisConstant.REDIS_KEY_STOCK_VERSION, idStr, stock.getVersion());
        }
    }
}
