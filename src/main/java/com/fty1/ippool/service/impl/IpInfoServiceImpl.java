package com.fty1.ippool.service.impl;

import com.fty1.ippool.IpAddressUtils;
import com.fty1.ippool.component.rabbitmq.RabbitmqProducer;
import com.fty1.ippool.component.rabbitmq.RabbitmqQueue;
import com.fty1.ippool.component.redis.RedisBitMapConfig;
import com.fty1.ippool.component.redis.RedisManager;
import com.fty1.ippool.entity.IpInfo;
import com.fty1.ippool.entity.IpInfoDO;
import com.fty1.ippool.repository.IpInfoRepository;
import com.fty1.ippool.service.IpInfoService;
import com.fty1.ippool.utils.Fty1JsonUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    private RedisManager redisManager;



    @Autowired
    private RabbitmqProducer rabbitmqProducer;

    @Override
    public void ipGather() {
        IpInfo ipInfo = IpAddressUtils.generateIPfo();
        if (ipInfo == null) {
            log.info("N");
            return;
        }

        /**
         * 如果Redis里面有这个IP信息就不发送
         */
        int hashCode = getHashCode(ipInfo);
        if(hashCode < 0){
            log.info("H:{}",ipInfo.toString());
            return;
        }

        boolean exists =  redisManager.query(RedisBitMapConfig.BITMAP_UNIQUE_IP_POOL_HASH_CODE,hashCode);
        if(exists){
            log.info("E");
            return;
        }
        ipInfo.setUniqueCode(hashCode);
        rabbitmqProducer.producer(RabbitmqQueue.QUEUE_POOL_IP_SOURCE, ipInfo);
    }

    /**
     * 根据Ip地址信息获取Hash值
     * @param ipInfo
     * @return
     */
    private int getHashCode(IpInfo ipInfo) {
        if(ipInfo == null){
            return 0;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ipInfo.getAddress());
        stringBuffer.append(ipInfo.getPort());
        return stringBuffer.toString().hashCode();
    }


    @Override
    public void ipGather(int n) {
        if (n <= 0) {
            return;
        }

        while (n-- > 0) {
            ipGather();
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public  void saveIpInfo(@NonNull IpInfo info) {
        IpInfoDO ipInfoDO = IpInfoDO.builder().address(info.getAddress()).createTime(new Date()).port(info.getPort()).build();
        ipInfoRepository.save(ipInfoDO);
        /**
         * 写入Redis
         */
        redisManager.mark(RedisBitMapConfig.BITMAP_UNIQUE_IP_POOL_HASH_CODE,info.getUniqueCode());
    }
}
