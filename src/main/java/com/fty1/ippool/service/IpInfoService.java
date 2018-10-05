package com.fty1.ippool.service;

import com.fty1.ippool.entity.IpInfo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public interface IpInfoService {

    /**
     * Ip收集
     */
    void ipGather();

    void ipGather(int n);

    void saveIpInfo(IpInfo obj);

    void consumerIpInfo(IpInfo info);
}
