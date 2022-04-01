package com.it.client;

import com.it.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class FanoutClient extends Thread{
    private String name;
    private Long waitTime;
    public FanoutClient(String name, Long waitTime){
        this.name = name;
        this.waitTime = waitTime;
    }
    @Override
    public void run() {
        Connection connection = ConnectionUtils.getConnection();
        try {
            Channel channel = connection.createChannel();
            /* 将通道绑定交换机 */
            channel.exchangeDeclare("logs","fanout");
            /* 生成临时队列 */
            String queue = channel.queueDeclare().getQueue();
            /* 将临时队列绑定通道 */
            /**
             * 参数1: 队列名称
             * 参数2: 交换机名称
             * 参数3: 路由名称
             */
            channel.queueBind(queue,"logs","");
            channel.basicConsume(queue,true,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(name + "消费消息: " + new String(body));
                }
            });

//            ConnectionUtils.closeConnection(connection,channel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FanoutClient("consumer1",0l).start();
        new FanoutClient("consumer2",0l).start();
    }
}
