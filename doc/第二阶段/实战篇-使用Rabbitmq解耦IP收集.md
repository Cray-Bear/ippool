#### 章节目标
* 引入Rabbitmq实现IP地址信息创建与存储解耦
#### 项目地址

构建视频：

代码地址：

##### 项目结构
```
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── fty1
    │   │           └── ippool
    │   │               ├── component   //组件包
    │   │               │   ├── package-info.java
    │   │               │   └── rabbitmq
    │   │               │       ├── ConfigRabbitmq.java   //Rabbitmq配置
    │   │               │       ├── RabbitmqConsumer.java //消息消费者
    │   │               │       ├── RabbitmqProducer.java //消息生产者
    │   │               │       └── RabbitmqQueue.java    //队列配置
    │   │               ├── config
    │   │               │   └── AppConfig.java            //系统配置
    │   │               ├── entity
    │   │               │   ├── IpInfoDO.java
    │   │               │   └── IpInfo.java
    │   │               ├── IpAddressUtils.java
    │   │               ├── IppoolApplication.java
    │   │               ├── repository
    │   │               │   └── IpInfoRepository.java
    │   │               ├── schedule
    │   │               │   └── ScheduleIpGather.java
    │   │               ├── service
    │   │               │   ├── impl
    │   │               │   │   └── IpInfoServiceImpl.java
    │   │               │   ├── IpInfoService.java
    │   │               │   └── package-info.java
    │   │               └── utils
    │   │                   └── Fty1JsonUtils.java  //Json格式化工具类
    │   └── resources
    │       ├── application.properties
```


##### 代码剖析

文件1：pom.xml
```
#Rabbitmq配置
spring.rabbitmq.host=127.0.0.1
spring.rabbitmq.port=5672
spring.rabbitmq.username=root
spring.rabbitmq.password=root

```
###### 剖析：增加了关于MQ的配置。这里username和password是在MQ安装之后时候设置的



文件2：IpInfoServiceImpl.java
```
    <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
```
###### 剖析：
- 增加Rabbitmq的一依赖，使用其他MQ注意看依赖


文件3：AppConfig.java
```
  @Configuration
  @Import(ConfigRabbitmq.class)
  public class AppConfig {

  }

```
###### 剖析：
- 配置思路是系统所有的配置都在这个类引用，模块处理好内部的引用，用一个类暴露出来，比如这
里引入了关于Rabbitmq的配置(ConfigRabbitmq.class)。



文件4：RabbitmqQueue.java
```
    public static final String QUEUE_POOL_IP_SOURCE="POOL_IP_SOURCE";

    @Bean
    public Queue queuePoolIpSource() {
        Queue queue = new Queue(QUEUE_POOL_IP_SOURCE);
        return queue;
    }
```
###### 剖析：
- 这里定义了队列的名字(POOL_IP_SOURCE),队列名字为常量便于外部调用

文件5：RabbitmqProducer.java
```
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public <T> void producer(@NonNull String routingKey, @NonNull T t) {
        rabbitTemplate.convertAndSend(routingKey, t);
    }
```
###### 剖析：
- 消息生产者,重点在AmqpTemplate,见《优化篇-AmqpTemplate那些事儿》

文件6：RabbitmqConsumer.java
```
package com.fty1.ippool.component.rabbitmq;

import com.fty1.ippool.entity.IpInfo;
import com.fty1.ippool.service.IpInfoService;
import com.fty1.ippool.utils.Fty1JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IpInfoService ipInfoService;

    @RabbitHandler
    public void consumerQueuePoolIpSource(IpInfo info) {
        log.info("消费者-consumerQueuePoolIpSource-参数:{}", Fty1JsonUtils.toJsonString(info));
        if (info == null) {
            log.info("消费者-consumerQueuePoolIpSource-结果:参数为空");
            return;
        }
        ipInfoService.saveIpInfo(info);
    }

}
```
###### 剖析：
- @RabbitListener注解指定了监听的队列
- @RabbitHandler注解指定了消息的实际消费者
- 对于这个需要考虑后期队列很多情况。


###### 分析：
- a.定时任务(ipGather)做了修改，只生产消息
- b.consumerQueuePoolIpSource()，存储IP信息时是有一次查库的
- c.扩展了ipGather()方法为ipGather(n)，传入每次消息的生产数量，在修改是一秒钟产生
一个IP信息，修改为10000时，会发现消费者消费速度跟不上，见视频
#### 项目总结
- 将之前IP地址信息从创建、验证、存储耦合在一起，修改为创建和存储分离。
- 数据查重依旧依赖Mysql














