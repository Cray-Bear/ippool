package com.fty1.ippool.service;

import com.fty1.ippool.entity.IpInfo;
import org.springframework.stereotype.Component;

@Component
public interface IpInfoService {

    /**
     * Ip收集
     */
    void ipGather();

    void saveIpInfo(IpInfo obj);
}
