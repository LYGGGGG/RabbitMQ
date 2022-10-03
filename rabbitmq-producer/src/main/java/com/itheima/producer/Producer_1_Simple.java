package com.itheima.producer;

/*
@author YG
@create 2022/9/30   22:10
*/

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_1_Simple {
    public static void main(String[] args) throws IOException, TimeoutException {
        //------1 创建连接------
        //1.1 创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        //1.2 设置参数
        factory.setHost("192.168.42.100");
        factory.setPort(5672);
        factory.setUsername("heima");
        factory.setPassword("heima");
        factory.setVirtualHost("/itcast");
        //1.3 创建连接
        Connection connection = factory.newConnection();
        //1.4 创建channel
        Channel channel = connection.createChannel();

        //------2 创建队列queue（暂时不用exchange）------
        /**
         * queueDeclare(String queue, boolean durable, boolean exclusive,boolean autoDelete, Map<String, Object> arguments)
         * queue:队列名称
         * durable:是否持久化，是则重启后，mq仍在
         * exclusive:是否独占，是则只有一个消费者监听这个队列
         * autoDelete:是否自动删除，是则没有consumer，就自动删除
         * arguments:参数
         */
        channel.queueDeclare("hello_world",true,false,false,null);
        //------3 发送消息------
        /**
         * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         * exchange:交换机名称，简单模式下使用默认交换机，设置为""
         * routingKey:路由名称，路由到对应的队列
         * props:一些配置
         * body:消息体，字节数组
         */
        String body = "Hello, RabbitMQ. My name is Yi";
        channel.basicPublish("","hello_world",null,body.getBytes());

        //------释放资源------
//        channel.close();
//        connection.close();
    }
}
