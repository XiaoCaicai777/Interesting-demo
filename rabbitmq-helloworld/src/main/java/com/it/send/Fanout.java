package com.it.send;

import com.it.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Fanout {

    public static void main(String[] args) {
        Connection connection = ConnectionUtils.getConnection();
        try {
            Channel channel = connection.createChannel();
            /**
             * 参数1: 交换机名称
             * 参数2: 交换机类型
             */
            channel.exchangeDeclare("logs","fanout");
            channel.basicPublish("logs","",null,"fanout type message".getBytes());
            ConnectionUtils.closeConnection(connection,channel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
