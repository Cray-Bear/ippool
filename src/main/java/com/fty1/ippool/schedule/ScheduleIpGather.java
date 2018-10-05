package com.fty1.ippool.schedule;

import com.fty1.ippool.service.IpInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * IP收集定时任务
 *
 * @author Crzy-Bear
 * @date 2018-10-02 4:10 PM
 **/
@Component
@Slf4j
public class ScheduleIpGather {

    @Autowired
    private IpInfoService ipInfoService;

    /**
     * IP收集定时任务处理类
     */
    @Scheduled(cron="0/1 * *  * * ? ")
    public void ipGather() {
        log.info("收集IP-开始-{}",System.currentTimeMillis());
        //收集IP的代码
        ipInfoService.ipGather(1000);
        log.info("收集IP-结束-{}",System.currentTimeMillis());
    }


}
