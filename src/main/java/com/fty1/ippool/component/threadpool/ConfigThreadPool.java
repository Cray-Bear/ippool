package com.fty1.ippool.component.threadpool;

import org.springframework.context.annotation.Import;

/**
 * 异步线程池配置
 *
 * @author Crzy-Bear
 * @date 2018-10-04 4:41 PM
 **/
@Import({ThreadPoolRabbitmqProducer.class,
        ThreadPoolRabbitmqConsumer.class})
public class ConfigThreadPool {
}
