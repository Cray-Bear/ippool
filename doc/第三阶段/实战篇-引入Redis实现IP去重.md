#### 章节目标
* 构建SpringBoot项目
* 使用Scheduled
* 使用Hibernate
* 存储IP信息到Mysql上

#### 项目地址

构建视频：

代码地址：

##### 项目结构
```
├── ippool.iml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── fty1
    │   │           └── ippool
    │   │               ├── entity
    │   │               │   ├── DO
    │   │               │   │   └── IpInfoDO.java         //操作数据层的类
    │   │               │   └── IpInfo.java               //IP信息
    │   │               ├── IpAddressUtils.java           //IP地址工具类
    │   │               ├── IppoolApplication.java        //项目启动类
    │   │               ├── repository
    │   │               │   └── IpInfoRepository.java     //相当于Dao
    │   │               ├── schedule
    │   │               │   └── ScheduleIpGather.java     //IP收集定时任务
    │   │               └── service
    │   │                   ├── impl
    │   │                   │   └── IpInfoServiceImpl.java//IP信息服务实现
    │   │                   ├── IpInfoService.java        //IP信息服务
    │   │                   └── package-info.java
    │   └── resources
    │       ├── application.properties                    //系统配置文件
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── com
                └── fty1
                    └── ippool
                        ├── IppoolApplicationTests.java
                        └── repository
                            └── IpInfoRepositoryTest.java
```


##### 代码剖析

文件1：ScheduleIpGather.java
```
    /**
     * IP收集定时任务处理类
     */
    @Scheduled(cron="0/1 * *  * * ? ")
    public void ipGather() {
        log.info("收集IP-开始-{}",System.currentTimeMillis());
        //收集IP的代码
        ipInfoService.ipGather();
        log.info("收集IP-结束-{}",System.currentTimeMillis());
    }
```
###### 剖析：就目前的结构来说：IP生成、查重、存储都一起的，注意这个定时任务是每秒钟执行一次。
ipInfoService.ipGather()要考虑线程安全问题。
当然也可以修改定时任务的执行方式



文件2：IpInfoServiceImpl.java
```

    private LinkedList<IpInfoDO> ips = new LinkedList<>();  //囤积的IP地址

    @Override
    public void ipGather() {
        IpInfo ipInfo = IpAddressUtils.generateIPfo();
        if(ipInfo == null){
            log.info("N");
            return;
        }
        IpInfoDO info = ipInfoRepository.

        findIpInfoDOByAddressAndPort(ipInfo.getAddress(),
        ipInfo.getPort());  //为了排版需要折行
        if (info != null) {
            log.info("E");
            return;
        }

        IpInfoDO ipInfoDO = IpInfoDO.builder().
        address(ipInfo.getAddress()).
        createTime(new Date()).
        port(ipInfo.getPort()).build();

        ips.add(ipInfoDO);
        System.out.println(ips.size());
        synchronized (this) {
            if(ips.size() > 10){
                ipInfoRepository.saveAll(ips);
                ips.clear();
            }
        }
    }
```
###### 剖析：
- 1.IpAddressUtils.generateIPfo()生成IP信息，见《优化篇-IP地址哪些事儿》
- 2.ipInfoRepository.findIpInfoDOByAddressAndPort(ipInfo.getAddress(),
ipInfo.getPort())这是Hibernate的高级语法，见《优化篇-Hibernate那些事儿》
- 3.IpInfoDO.builder()....build(),见《优化篇-Lombok那些事儿》
- 4.synchronized (this),见《优化篇-Synchronized哪些事儿》

看完之后，我们会发现：这段代码有问题！！！！
###### 注意：
- 1.表ip_info(ip信息存放的表)是没有索引的。
###### 分析：
- a.定时任务(ipGather)是每秒钟执行一次
- b.ipGather的代码逻辑是先追条数据库查重，存放到LinkedList里面，大于10条时一起保存
到数据库中

这个过程中会存在LinkedList中有重复的数据，因为LinkedList中的数据还没存储到Mysql中，
其他线程(另一个定时任务)执行到这里的时候会将生成的IP信息加到LinkedList中。

就目前的代码来看，首先生成的IP地址不会重复，另外ipGather()只有一个地方调用，就系统整体
而言，不会出现Mysql中的IP信息重复。

#### 项目总结
- 一秒一个IP生成速度无发现生产需要
- IP地址信息从创建、验证、存储都在一段代码里面，耦合严重














