package com.fty1.ippool.repository;

import com.alibaba.fastjson.JSON;
import com.fty1.ippool.IppoolApplicationTests;
import com.fty1.ippool.entity.DO.IpInfoDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IpInfoRepositoryTest  extends IppoolApplicationTests {


    @Autowired
    private IpInfoRepository ipInfoRepository;

    @Test
    public void findIpInfoDOByAddressAndPort() {
        IpInfoDO ipInfoDO = new IpInfoDO();
        ipInfoDO.setAddress("127.0.0.1");
        ipInfoDO.setPort(8080);
        IpInfoDO re = ipInfoRepository.save(ipInfoDO);
        System.out.println(JSON.toJSONString(re));
        IpInfoDO res = ipInfoRepository.findIpInfoDOByAddressAndPort(ipInfoDO.getAddress(), ipInfoDO.getPort());
        System.out.println(JSON.toJSONString(res));

    }
}