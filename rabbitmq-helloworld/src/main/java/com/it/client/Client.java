package com.it.client;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Client {

    public static void main(String[] args){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.222.154");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/rabbitmq-helloworld");
        connectionFactory.setUsername("helloworld");
        connectionFactory.setPassword("123456");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("hello",false,false,false,null);
            /**
             * 消费消息
             * 参数1: 消费那个队列的消息, 队列名称
             * 参数2: 开启消息的自动确认机制
             * 参数3: 消费时的回调接口
             */
            channel.basicConsume("hello",true,new DefaultConsumer(channel){
                @Override // 最后一个参数,消息队列中取出的消息
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(new String(body));
                }
            }  );
            // 如果不关闭, 就会一直监听这个队列
//            channel.close();
//            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
