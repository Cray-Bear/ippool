package com.fty1.ippool.entity.DO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * IP信息
 *
 * @author Crzy-Bear
 * @date 2018-10-02 3:39 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ip_info")
public class IpInfoDO implements Serializable {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * IP地址
     */
    private String address;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 创建时间
     */
    private Date createTime;
}
