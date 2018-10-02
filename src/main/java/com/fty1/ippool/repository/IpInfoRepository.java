package com.fty1.ippool.repository;

import com.fty1.ippool.entity.IpInfoDO;
import org.springframework.data.repository.CrudRepository;

/**
 * IpInfoDO数据访问组件
 *
 * @author Crzy-Bear
 * @date 2018-10-02 3:44 PM
 **/
public interface IpInfoRepository extends CrudRepository<IpInfoDO, Long> {


    /**
     * 根据IP地址和端口获取IP信息
     * @param address
     * @param port
     * @return
     */
    IpInfoDO findIpInfoDOByAddressAndPort(String address,Integer port);
}
