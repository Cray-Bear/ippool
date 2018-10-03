package com.fty1.ippool;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot
 * @author Cray-Bear
 * @date 2018-10-02 15:39:05
 */
@SpringBootApplication
@EnableScheduling
@EnableRabbit
public class IppoolApplication {
	public static void main(String[] args) {
		SpringApplication.run(IppoolApplication.class, args);
	}
}
