package com.hanc.seckill.seckillconsumer.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON帮助类
 */
public final class JsonUtil {

    private JsonUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 将JSON字符串转换为map
     */
    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = (Map<String, Object>) JSONObject.parse(json);
        return map;
    }

    /**
     * 将JSON字符串转换为map
     */
    public static Map<String, String> jsonToStrMap(String json) {
        Map<String, Object> map = jsonToMap(json);
        // map value类型转换
        Map<String, String> newMap = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            newMap.put(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
        }
        return newMap;
    }

    /**
     * 将Map转JSON字符串
     */
    public static String mapToJson(Map<String, Object> map) {
        return JSONObject.toJSONString(map);
    }

    /**
     * 将Map转JSON字符串
     */
    public static String strMapToJson(Map<String, String> map) {
        // map value类型转换
        Map<String, Object> newMap = new HashMap<String, Object>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            newMap.put(entry.getKey(), entry.getValue());
        }
        return mapToJson(newMap);
    }

    /**
     * 将obj转换成map
     */
    public static Map<String, Object> objToMap(Object obj) {
        return jsonToMap(objToJson(obj));
    }

    /**
     * 将map转换成obj
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
        return jsonToBean(objToJson(map), beanClass);
    }

    /**
     * 对象转json
     * @param features 序列化方式，可传可不传，若不过滤null，则加入SerializerFeature.WriteMapNullValue
     */
    public static String objToJson(Object obj, SerializerFeature... features) {
        return JSONObject.toJSONString(obj, features);
    }


    /**
     * 将json字符串转换为对象
     */
    public static <T> T jsonToBean(String jsonStr, Class<T> beanClass) {
        return (T) JSONObject.parseObject(jsonStr, beanClass);
    }


    /**


    /**
     * 对象转换为json
     * @param o  对象
     * @return
     */
    public static String toString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception var2) {
            throw new IllegalStateException(var2);
        }
    }

    /**
     * 测试JSON格式是否正常
     */
    public static boolean isJson(String json) {
        try {
            JSONObject.parse(json);
            return true;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

}
