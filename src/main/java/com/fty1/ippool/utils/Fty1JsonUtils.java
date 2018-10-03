package com.fty1.ippool.utils;

import com.alibaba.fastjson.JSON;

/**
 * Json转换处理类
 *
 * @author Crzy-Bear
 * @date 2018-10-03 2:12 PM
 **/
public class Fty1JsonUtils {

    /**
     * 对象转JSON字符串
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String toJsonString(T t) {
        return JSON.toJSONString(t);
    }
}
