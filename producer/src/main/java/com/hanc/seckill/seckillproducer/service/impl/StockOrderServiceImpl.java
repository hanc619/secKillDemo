package com.hanc.seckill.seckillproducer.service.impl;

import com.hanc.mq.core.model.MQSendResult;
import com.hanc.mq.core.producer.PublishMQClient;
import com.hanc.seckill.seckillproducer.dao.StockOrderDAO;
import com.hanc.seckill.seckillproducer.entity.OnsTopic;
import com.hanc.seckill.seckillproducer.entity.Stock;
import com.hanc.seckill.seckillproducer.entity.StockOrder;
import com.hanc.seckill.seckillproducer.exception.ApiException;
import com.hanc.seckill.seckillproducer.exception.EC;
import com.hanc.seckill.seckillproducer.service.StockOrderService;
import com.hanc.seckill.seckillproducer.util.redis.RedisConstant;
import com.hanc.seckill.seckillproducer.util.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: cuill
 * @date: 2018/8/16 10:08
 */
@Service
public class StockOrderServiceImpl implements StockOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderServiceImpl.class);

    @Autowired
    private PublishMQClient publishMqClient;

    @Autowired
    private OnsTopic onsTopic;

    @Autowired
    private StockOrderDAO stockOrderDAO;

    @Resource(name = "redisUtil")
    private RedisUtil redisUtil;


    @Override
    public void createOrder(int sid) {

        Stock stock = this.checkStockByRedis(sid);

        StockOrder order = new StockOrder();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        order.setStatus(1);
        int id = stockOrderDAO.insertSelective(order);
        MQSendResult result = null;
        if (id > 0) {
            result = publishMqClient.sendMessage(onsTopic.getMsgTopic(), "placeOrder",stock, String.valueOf(sid));
        }
        if (result == null) {
            stockOrderDAO.updateStatusByPrimaryKey(id);
            LOGGER.info("创建订单失败");
        }
    }

    /**
     * 从redis里面检查库存
     *
     * @param sid 商品的id
     */
    private Stock checkStockByRedis(int sid) {
        long count = 0L;
        long sale = 0L;
        long version = 0L;
        if (!redisUtil.hexists(RedisConstant.REDIS_KEY_STOCK_COUNT, String.valueOf(sid))
                || !redisUtil.hexists(RedisConstant.REDIS_KEY_STOCK_SALE, String.valueOf(sid))
                || !redisUtil.hexists(RedisConstant.REDIS_KEY_STOCK_VERSION, String.valueOf(sid))) {
             publishMqClient.sendMessage(onsTopic.getMsgTopic(),"stockMessage", sid, String.valueOf(sid));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOGGER.error("thread InterruptedException, sid is [{}]", sid, e);
            }
        }
        try {
            count = redisUtil.hincrby(RedisConstant.REDIS_KEY_STOCK_COUNT, String.valueOf(sid), 0);
            sale = redisUtil.hincrby(RedisConstant.REDIS_KEY_STOCK_SALE, String.valueOf(sid), 0);
            version = redisUtil.hincrby(RedisConstant.REDIS_KEY_STOCK_VERSION, String.valueOf(sid), 0);
        } catch (Exception e) {
            LOGGER.error("stock count sale redis get count fail, sid is [{}]", sid, e);
        }
        if (count <= sale) {
            throw new ApiException(EC.FAILED_BUSINESS.getCode(), "商品已经被抢光");
        }
        return new Stock(sid, count, sale, version);
    }
}
