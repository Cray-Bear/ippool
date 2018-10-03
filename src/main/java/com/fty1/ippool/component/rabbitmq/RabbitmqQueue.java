package com.fty1.ippool.component.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * Rabbitmq队列模式
 *
 * @author Crzy-Bear
 * @date 2018-10-03 1:36 PM
 **/
public class RabbitmqQueue {

    /**
     * IP收集队列(所有ip信息的进入应用的入口)
     */
    public static final String QUEUE_POOL_IP_SOURCE="POOL_IP_SOURCE";

    @Bean
    public Queue queuePoolIpSource() {
        Queue queue = new Queue(QUEUE_POOL_IP_SOURCE);
        return queue;
    }

}
