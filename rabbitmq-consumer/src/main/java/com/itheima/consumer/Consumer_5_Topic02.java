package com.itheima.consumer;

/*
@author YG
@create 2022/10/1   0:27
*/

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_5_Topic02 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/itcast");
        factory.setHost("192.168.42.100");
        factory.setPort(5672);
        factory.setUsername("heima");
        factory.setPassword("heima");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName_1 = "test_topic_queue_1";
        String queueName_2 = "test_topic_queue_2";
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                /**
                 * consumerTag: 标识
                 * envelope: 获取一些信息
                 * properties: 配置信息
                 * body: 数据
                 */
                System.out.println(queueName_2 + ", body: " + new String(body));
                System.out.println("将日志信息保存到数据库");
            }
        };
        channel.basicConsume(queueName_2, true, consumer);

        //关闭资源？不需要.
    }
}
