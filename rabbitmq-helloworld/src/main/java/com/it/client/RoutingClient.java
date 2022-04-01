package com.it.client;

import com.it.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RoutingClient implements Runnable
{
    private String name;
    private String routName;

    public RoutingClient(String name, String routName) {
        this.name = name;
        this.routName = routName;
    }

    @Override
    public void run() {
        Connection connection = ConnectionUtils.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("user","direct");
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue,"user",routName);
            channel.basicConsume(queue,true,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(name + " 消费消息: " + new String(body));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new RoutingClient("user1","user1")).start();
        new Thread(new RoutingClient("user2","user2")).start();
    }
}
