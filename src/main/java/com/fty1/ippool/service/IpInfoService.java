package com.fty1.ippool.service;

import org.springframework.stereotype.Component;

@Component
public interface IpInfoService {

    /**
     * Ip收集
     */
    void ipGather();
}
