package com.it.send;

import com.it.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work {
    public static void main(String[] args) {
        Connection connection = ConnectionUtils.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare("work",false,false,false,null);
            //生产消息
            for (int i = 0; i < 20; i++) {
                channel.basicPublish("","work",null,"hello world".getBytes());
            }
            ConnectionUtils.closeConnection(connection,channel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
