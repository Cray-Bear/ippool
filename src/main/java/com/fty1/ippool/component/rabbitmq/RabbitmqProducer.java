package com.fty1.ippool.component.rabbitmq;

import com.fty1.ippool.service.IpInfoService;
import lombok.NonNull;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Rabbitmq消息生产者
 *
 * @author Crzy-Bear
 * @date 2018-10-03 1:46 PM
 **/
@Component
public class RabbitmqProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     *
     * @param routingKey
     * @param t
     * @param <T>
     */
    public <T> void producer(@NonNull String routingKey,@NonNull T t){
        rabbitTemplate.convertAndSend(routingKey,t);
    }

}
