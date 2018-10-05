package com.fty1.ippool.component.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Rabbitmq消息消费线程池
 *
 * @author Crzy-Bear
 * @date 2018-10-04 4:45 PM
 **/
public class ThreadPoolRabbitmqConsumer {

    /**
     * 系统线程的bean Id
     */
    public static final String THREAD_POOL_RABBITMQ_CONSUMER="threadPoolRabbitmqConsumer";

    @Bean(value = THREAD_POOL_RABBITMQ_CONSUMER)
    public Executor threadPoolRabbitmqConsumer() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(160);
        executor.setMaxPoolSize(320);
        executor.setQueueCapacity(80);
        executor.setThreadNamePrefix("threadPoolRabbitmqConsumer-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(20);
        executor.initialize();
        return executor;
    }

}
