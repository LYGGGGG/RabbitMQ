package com.itheima.producer;

/*
@author YG
@create 2022/9/30   22:10
*/

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_4_Routing {
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

        //------2 创建交换机exchange------
        /**
         * exchangeDeclare(String var1, BuiltinExchangeType var2)
         * 1、交换机的名称
         * 2、交换机的类型
         fanout：对应的rabbitmq的工作模式是 publish/subscribe
         direct：对应的Routing	工作模式
         topic：对应的Topics工作模式
         headers： 对应的headers工作模式
         */
        String exchangeName = "test_routing_direct";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);

        //------3 创建队列queue（暂时不用exchange）------
        /**
         * queueDeclare(String queue, boolean durable, boolean exclusive,boolean autoDelete, Map<String, Object> arguments)
         * queue:队列名称
         * durable:是否持久化，是则重启后，mq仍在
         * exclusive:是否独占，是则只有一个消费者监听这个队列
         * autoDelete:是否自动删除，是则没有consumer，就自动删除
         * arguments:参数.
         */
        String queueName_1 = "test_direct_queue_1";
        String queueName_2 = "test_direct_queue_2";
        channel.queueDeclare(queueName_1, true, false, false, null);
        channel.queueDeclare(queueName_2, true, false, false, null);

        //------4 绑定交换机和队列------
        /**
         * queueBind(String queue, String exchange, String routingKey)
         * queue:队列名称
         * exchange:交换机名称
         * routingKey:路由键，绑定规则；如果交换机类型为fanout，则routingKey为空
         */
        channel.queueBind(queueName_1, exchangeName, "error");

        channel.queueBind(queueName_2, exchangeName, "info");
        channel.queueBind(queueName_2, exchangeName, "error");
        channel.queueBind(queueName_2, exchangeName, "warning");

        //------5 发送消息------
        /**
         * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         * exchange，交换机，如果不指定将使用mq的默认交换机（设置为""）
         * routingKey，路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
         * props，消息的属性
         * body，消息内容
         */
        String body = "日志信息：张三调用了findAll方法...日志级别：warning...";
        channel.basicPublish(exchangeName, "warning", null, body.getBytes());

        //------6 释放资源------
        channel.close();
        connection.close();
    }
}
