1. dubbo
dubbo底层是采用 SPI和netty机制来实现服务发现和高性能
dubbo应用,推荐采用三层结构的设计,将model类和接口类抽出来,服务端和消费端只针对抽出来的接口模块进行比编程,
两边只关注具体业务的实现, 不用在一具体的实现细节;
与springboot的集成:
1.1: 需要导入相关的安装包依赖:
    1.1.1: 对于服务者而言 需要导入 dubbo-spring-boot-start依赖 然后需要导入对应的
            zookeeper客户端依赖:
             <dependency>
                        <groupId>org.apache.dubbo</groupId>
                        <artifactId>dubbo-spring-boot-starter</artifactId>
                        <version>2.7.3</version>
                    </dependency>
                    <!-- zk的依赖 -->
                    <dependency>
                        <groupId>org.apache.dubbo</groupId>
                        <artifactId>dubbo-dependencies-zookeeper</artifactId>
                        <version>2.7.3</version>
                        <type>pom</type>
                        <exclusions>
                            <exclusion>
                                <groupId>org.slf4j</groupId>
                                <artifactId>slf4j-log4j12</artifactId>
                            </exclusion>
                        </exclusions>
                    </dependency>
                    
    1.1.2: 服务端的配置: 
        需要给服务段配置相关的使用协议,通信端口, 服务名,以及zookeeper的注册地址,一般注册到zookeeper的时间
        比较长,所以建议配置一个超时时间 ,这样一个基本的服务就配置好了
     
    1.1.3: 对于服务端的实现, 需要给服务端的接口实现加上dubbo的@Service注解, 然后在启动类中加入 @EnableDubbbo的配置
        
    1.2.1: 对于客户端而言,需要导入dubbo相关依赖以及对应的zookeeper客户端 跟服务端一样;
    
    1.2.2: 客户端的配置: 也是需要声明注册端口, 以及使用的协议和服务名,包括zookeeper的注册地址,
           客户端引入服务端的接口实现  ,只需要加上@Reference注解即可,启动类上加上@EnableDubbo注解,
           这样一个基本的客户端就搭建完成;
           
2. dubbo总结: dubbo跟springcloud类似 提供一系列的微服务治理方案


3. rabbitmq
    AMQP协议中的一种实现, rabbitmq具有稳定可靠的消息消费机制, 但是性能较差 
    消息-生成者模型: 
         普通-> 消费者与队列直连
         工作(work) -> 一个队列绑定多个消费者
         广播(fanout)  -> 一个交换机绑定多个队列, 不同队列绑定消费者
         直连(direct) -> 一个交换机绑定多个队列, 根据路由key分发消息到指定队列,队列绑定不同的消费者
         topic模式 -> 在直连模式上增加了路由key的通配符指定 ,* (0个或多个字符) #(一个或多个字符)
         
    基本使用方式: 创建rabbitmq工厂类 ,配置rabbitmq服务 包括host ,port, 以及虚拟主机 user, password等
    队列可以直接跟消费者/生产者交互,消费者只能跟队列交互, 生产者可以和交换机/队列交互,而交换机和队列时通过"通道"交互

    