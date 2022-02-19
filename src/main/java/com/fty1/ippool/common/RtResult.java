package com.fty1.ippool.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 程序运行是结果
 *
 * @author Crzy-Bear
 * @date 2018-10-03 2:40 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RtResult<T> {
    private Boolean success;
    private T data;

    public static <T> RtResult<T> success(T data) {
        return new RtResult<>(true, data);
    }

    public static <T> RtResult<T> failure(T data) {
        return new RtResult<>(true, data);
    }
}
