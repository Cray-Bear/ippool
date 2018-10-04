package com.fty1.ippool.entity;

import lombok.*;

import java.io.Serializable;

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
public class IpInfo implements Serializable {

    private static final long serialVersionUID = 1668710108150674437L;

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

    /**
     * IP信息唯一值（地址加端口直接hash）
     */
    private Integer uniqueCode;

}
