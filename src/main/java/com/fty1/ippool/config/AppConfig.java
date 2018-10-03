package com.fty1.ippool.config;

import com.fty1.ippool.component.rabbitmq.ConfigRabbitmq;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring所有集成配置
 *
 * @author Crzy-Bear
 * @date 2018-10-02 8:10 PM
 **/

@Configuration
@Import(ConfigRabbitmq.class)
public class AppConfig {

}
