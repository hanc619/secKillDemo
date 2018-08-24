package com.hanc.seckill.seckillproducer.util.redis;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hanc.seckill.seckillproducer.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 */
@Service
public class RedisUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存-无超时
     *
     * @param key
     * @param value
     */
    public void set(final String key, Object value) throws Exception {
        try {
            if (key == null || value == null) {
                LOGGER.error("redis set, key or value is null");
                return;
            }
            String jsonValue = JsonUtil.objToJson(value, SerializerFeature.WriteMapNullValue);
            redisTemplate.opsForValue().set(key, jsonValue);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * 配合hgetNum使用，设置计数，支持加减
     */
    public Long hincrby(final String name, final String field, final long val) {
        return (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                StringRedisSerializer keySerializer = new StringRedisSerializer();
                byte[] name_ = keySerializer.serialize(name);
                byte[] field_ = keySerializer.serialize(field);
                return connection.hIncrBy(name_, field_, val);
            }
        });
    }

    /**
     * 删除一个hash数据结构的key值
     * @param name   名称
     * @param field  key值
     * @param <T>
     * @return
     */
    public <T> Long hdel(final String name, final String field) {
        return (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                StringRedisSerializer keySerializer = new StringRedisSerializer();
                byte[] name_ = keySerializer.serialize(name);
                byte[] field_ = keySerializer.serialize(field);
                return connection.hDel(name_,field_);
            }
        });
    }


    /**
     * 配合hincrby方法,用于获取计数值
     */
    public Long hgetNum(final String name, final String field) throws UnsupportedEncodingException {
        return (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                StringRedisSerializer keySerializer = new StringRedisSerializer();
                byte[] name_ = keySerializer.serialize(name);
                byte[] field_ = keySerializer.serialize(field);
                byte[] value_ = connection.hGet(name_, field_);
                try {
                    return Long.parseLong(new String(value_, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error(e.getMessage());
                }
                return null;
            }
        });
    }

    /**
     * 判断是否存在
     */
    public <T> boolean hexists(final String name, final String field) {
        return (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                StringRedisSerializer keySerializer = new StringRedisSerializer();
                byte[] name_ = keySerializer.serialize(name);
                byte[] field_ = keySerializer.serialize(field);
                if (Boolean.TRUE.equals(connection.hExists(name_,field_))) {
                    return true;
                }else{
                    return false;
                }
            }
        });
    }

    /**
     * 生成redis hash格式数据 用于存放java 对象或者String
     *
     * @param name  hash的名称
     * @param field hash的key
     * @param val   hash的值
     * @param <T>   泛型
     * @return
     */
    public <T> boolean hset(final String name, final String field, final T val) {
        return (Boolean) this.redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisSerializer keySerializer = new StringRedisSerializer();
                JdkSerializationRedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
                byte[] name_ = keySerializer.serialize(name);
                byte[] field_ = keySerializer.serialize(field);
                byte[] value_ = valueSerializer.serialize(val);
                return connection.hSet(name_, field_, value_);
            }
        });
    }


    /**
     * 写入缓存-有超时
     *
     * @param key
     * @param value
     * @param offset 过时时间，以秒为单位
     */
    public void set(final String key, Object value, long offset) throws Exception {
        try {
            if (key == null || value == null) {
                LOGGER.error("redis set, key or value is null");
                return;
            }
            String jsonValue = JsonUtil.objToJson(value, SerializerFeature.WriteMapNullValue);
            redisTemplate.opsForValue().set(key, jsonValue, offset, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new Exception(e);
        }
    }


    /**
     * 读取缓存
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T get(String key, Class<T> clazz) throws Exception {
        try {
            LOGGER.debug("redis get, key:" + key + ",clazz:" + clazz.getName().toString());
            Object val = redisTemplate.boundValueOps(key).get();
            return val == null ? null : JsonUtil.jsonToBean(val.toString(), clazz);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * 删除，根据key精确匹配
     *
     * @param key
     */
    public void del(String... key) throws Exception {
        try {
            LOGGER.debug("redis del, key:" + key);
            redisTemplate.delete(Arrays.asList(key));
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * 删除，根据key精确匹配
     *
     * @param key
     */
    public void del(List<String> key) throws Exception {
        try {
            LOGGER.debug("redis del, key:" + key);
            redisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * 批量删除，根据key模糊匹配
     *
     * @param pattern
     */
    public void delWithPattern(String... pattern) throws Exception {
        try {
            LOGGER.debug("redis del, pattern:" + pattern);
            for (String kp : pattern) {
                redisTemplate.delete(redisTemplate.keys(kp));
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * key是否存在
     *
     * @param key key值
     */
    public boolean exists(String key) {
        try {
            LOGGER.debug("redis isExistsOrNot, key:" + key);
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return false;
    }

    /**
     * 模糊查询键
     *
     * @param pattern
     * @return
     * @throws Exception
     */
    public Set keysWithPattern(String... pattern) throws Exception {
        Set<String> keys = new HashSet<>();
        try {
            LOGGER.debug("redis get keys with pattern, pattern:" + pattern);
            for (String kp : pattern) {
                keys.addAll(redisTemplate.keys(kp));
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new Exception(e);
        }
        return keys;
    }


    /**
     * @return
     */
    public long dbSize() throws Exception {
        try {
            return (long) redisTemplate.execute(new RedisCallback() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.dbSize();
                }
            });
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * @return
     */
    public String ping() throws Exception {
        try {
            return (String) redisTemplate.execute(new RedisCallback() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {

                    return connection.ping();
                }
            });
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new Exception(e);
        }
    }
}