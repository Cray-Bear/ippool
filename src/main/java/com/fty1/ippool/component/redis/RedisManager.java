package com.fty1.ippool.component.redis;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis处理类
 *
 * @author Crzy-Bear
 * @date 2018-10-04 1:25 AM
 **/
@Component
public class RedisManager {


    @Autowired
    private StringRedisTemplate stringStringRedisTemplate;

    /**
     * 标记偏移量（offset）在在bitMapKey中的值为true
     * @param bitMapKey
     * @param offset
     * @return
     */
    public boolean mark(@NonNull String bitMapKey, int offset) {
        return mark(bitMapKey,offset,true);
    }

    /**
     * 标记偏移量（offset）在在bitMapKey中的值为(value)
     * @param bitMapKey
     * @param offset
     * @param value
     * @return
     */
    public boolean mark(@NonNull String bitMapKey, int offset,boolean value) {
        return stringStringRedisTemplate.opsForValue().setBit(bitMapKey, offset, value);
    }

    /**
     * 获取偏移量（offset）在bitMapKey中的值
     * @param bitMapKey
     * @param offset
     * @return
     */
    public boolean query(@NonNull String bitMapKey, int offset) {
        return stringStringRedisTemplate.opsForValue().getBit(bitMapKey, offset);
    }


}
