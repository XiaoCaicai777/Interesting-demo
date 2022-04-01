package com.it.send;

import com.it.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 路由直连类型
 */
public class Routing {
    public static void main(String[] args) {
        Connection connection = ConnectionUtils.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("user","direct");
            channel.basicPublish("user","user1",null,"user1的信息".getBytes());
            channel.basicPublish("user","user2",null,"user2的信息".getBytes());
            ConnectionUtils.closeConnection(connection,channel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
