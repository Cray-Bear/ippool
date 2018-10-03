package com.fty1.ippool.service.impl;

import com.fty1.ippool.IpAddressUtils;
import com.fty1.ippool.component.rabbitmq.RabbitmqProducer;
import com.fty1.ippool.component.rabbitmq.RabbitmqQueue;
import com.fty1.ippool.entity.IpInfoDO;
import com.fty1.ippool.entity.IpInfo;
import com.fty1.ippool.repository.IpInfoRepository;
import com.fty1.ippool.service.IpInfoService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;

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


    @Autowired
    private RabbitmqProducer rabbitmqProducer;


    private LinkedList<IpInfoDO> ips = new LinkedList<>();



    @Override
    public void ipGather() {
        IpInfo ipInfo = IpAddressUtils.generateIPfo();
        if(ipInfo == null){
            log.info("N");
            return;
        }
        rabbitmqProducer.producer(RabbitmqQueue.QUEUE_POOL_IP_SOURCE,ipInfo);
    }


    @Override
    public void saveIpInfo(@NonNull IpInfo info) {

        IpInfoDO ipInfo = ipInfoRepository.findIpInfoDOByAddressAndPort(info.getAddress(), info.getPort());
        if (info != null) {
            log.info("E");
            return;
        }

        IpInfoDO ipInfoDO = IpInfoDO.builder().address(ipInfo.getAddress()).createTime(new Date()).port(ipInfo.getPort()).build();
        ips.add(ipInfoDO);
        System.out.println(ips.size());
        synchronized (this) {
            if(ips.size() > 10){
                ipInfoRepository.saveAll(ips);
                ips.clear();
            }
        }
    }
}
