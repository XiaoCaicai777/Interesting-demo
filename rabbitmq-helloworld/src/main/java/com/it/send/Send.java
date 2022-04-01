package com.it.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Send {

    @Test
    public void provider(){
        /* 创建工厂连接对象 */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        /* 设置rabbitmq主机 */
        connectionFactory.setHost("192.168.222.154");
        /* 设置端口 */
        connectionFactory.setPort(5672);
        /* 设置虚拟主机  建议一个项目对应一个虚拟主机 */
        connectionFactory.setVirtualHost("/rabbitmq-helloworld");
        /* 设置用户名 */
        connectionFactory.setUsername("helloworld");
        /* 设置用户名密码 */
        connectionFactory.setPassword("123456");
        /* 获取连接对象 */
        try {
            Connection connection = connectionFactory.newConnection();
            /* 获取连接通道 */
            Channel channel = connection.createChannel();
            /* 创建通道后需要绑定队列  这里创建的是直连队列 如果没有队列,将会创建一个队列
            *   注意消费者和生产者的队列的属性需要保持一致
            * */
            //参数一: 队列名
            //参数二: 是否持久化 ,true是持久化 false不持久化, 如果为true时, 服务断掉或者当季后, 会将队列的内容持久化到磁盘, 服务重启后将进行恢复
            //参数三: 是否时独占队列
            //参数四: 消费完是否删除队列
            //参数五: 额外参数
            channel.queueDeclare("hello",false,false,false,null);
            //参数: 交换机名称, 队列名称, 传递消息额外设置, 消息的内容
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicPublish("","hello",null,"hello rabbitmq".getBytes());
            }
            //发送完消息就可以关闭通道
            channel.close();
            //关闭通道后就关闭连接
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
