package com.fty1.ippool.component.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Rabbitmq消息创建线程池
 *
 * @author Crzy-Bear
 * @date 2018-10-04 4:44 PM
 **/
public class ThreadPoolRabbitmqProducer {

    /**
     * 系统线程的bean Id
     */
    public static final String THREAD_POOL_RABBITMQ_PRODUCER = "threadPoolRabbitmqProducer";

    @Bean(value = THREAD_POOL_RABBITMQ_PRODUCER)
    public Executor threadPoolRabbitmqProducer() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(160);
        executor.setMaxPoolSize(320);
        executor.setQueueCapacity(80);
        executor.setThreadNamePrefix("threadPoolRabbitmqProducer-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(20);
        executor.initialize();
        return executor;
    }
}
