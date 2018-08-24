package com.hanc.seckill.seckillconsumer.util.redis;

/**
 * Redis key 统一设置点(全以redis_key开头,键值相同)
 */
public final class RedisConstant {

    private RedisConstant() {
    }

    /**
     * 库存
     */
    public static final String REDIS_KEY_STOCK_COUNT = "stock_count_";

    /**
     * 已出售数量
     */
    public static final String REDIS_KEY_STOCK_SALE = "stock_sale_";


    /**
     * 乐观锁更新的版本号
     */
    public static final String REDIS_KEY_STOCK_VERSION= "stock_version_";
}
