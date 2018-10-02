package com.fty1.ippool.entity;

import lombok.*;

/**
 * IP信息基础类
 *
 * @author Crzy-Bear
 * @date 2018-10-02 4:26 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IpInfo {
    /**
     * IP地址
     */
    @NonNull
    private String address;

    /**
     * 端口
     */
    @NonNull
    private Integer port;
}
