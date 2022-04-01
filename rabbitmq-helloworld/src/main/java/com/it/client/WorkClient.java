package com.it.client;

import com.it.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class WorkClient implements Runnable{
    private String name;
    private Long waitTime;
    public WorkClient(String name,Long waitTime){
        this.name = name;
        this.waitTime = waitTime;
    }
    public static void main(String[] args) {
        WorkClient consumer1 = new WorkClient("consumer1",1000l);
        WorkClient consumer2 = new WorkClient("consumer2",0l);
        // 平均消费(一次性已经将消息拿到了 ,只是消费得慢)? 如何做到能者多劳呢
        /**
         * 将自动消息确认关闭, 进行手动消息确认, 并设置每次只消费一个消息
         */
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }

    @Override
    public void run() {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare("work",false,false,false,null);
            //第二参数,消息自动确认机制,就是队列只管将消息分发到消费者, 不管消费者是否成功处理完成(这样就导致了消息消费失败,消息丢失的问题)
            //控制消费者没只能消费一个
            channel.basicQos(1);
            channel.basicConsume("work",false,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + "收到消息: " + new String (body));
                    //手动确认,参数1: 手动确认消息标识, 参数2: false 每次确认一个
                    this.getChannel().basicAck(envelope.getDeliveryTag(),false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
