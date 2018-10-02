package com.fty1.ippool.service.impl;

import com.fty1.ippool.IpAddressUtils;
import com.fty1.ippool.entity.DO.IpInfoDO;
import com.fty1.ippool.entity.IpInfo;
import com.fty1.ippool.repository.IpInfoRepository;
import com.fty1.ippool.service.IpInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IpInfo服务实现类
 *
 * @author Crzy-Bear
 * @date 2018-10-02 4:15 PM
 **/
@Service
@Slf4j
public class IpInfoServiceImpl implements IpInfoService {


    @Autowired
    private IpInfoRepository ipInfoRepository;


    @Override
    public void ipGather() {
        IpInfo ipInfo = IpAddressUtils.generateIPfo();
        if(ipInfo == null){
            return;
        }
        IpInfoDO ipInfoDO = IpInfoDO.builder().address(ipInfo.getAddress()).port(ipInfo.getPort()).build();
        ipInfoRepository.save(ipInfoDO);
    }
}
