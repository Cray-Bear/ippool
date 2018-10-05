package com.fty1.ippool.component.rabbitmq;

import com.fty1.ippool.component.redis.RedisBitMapConfig;
import com.fty1.ippool.component.redis.RedisManager;
import com.fty1.ippool.component.threadpool.ThreadPoolRabbitmqConsumer;
import com.fty1.ippool.entity.IpInfo;
import com.fty1.ippool.service.IpInfoService;
import com.fty1.ippool.utils.Fty1JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Rabbitmq消息消费者
 *
 * @author Crzy-Bear
 * @date 2018-10-03 1:48 PM
 **/
@Component
@Slf4j
@RabbitListener(queues = {RabbitmqQueue.QUEUE_POOL_IP_SOURCE})
public class RabbitmqConsumer {


    @Autowired
    private RedisManager redisManager;

    @Autowired
    private IpInfoService ipInfoService;

    @RabbitHandler
    public void consumerQueuePoolIpSource(IpInfo info) {
        if (info == null) {
            log.info("消费者-consumerQueuePoolIpSource-结果:参数为空");
            return;
        }
        ipInfoService.consumerIpInfo(info);


    }

}
