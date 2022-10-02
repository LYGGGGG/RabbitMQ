package com.itheima.rabbitmq.listener;

/*
@author YG
@create 2022/10/3   0:52
*/

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class SpringQueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println(new String(message.getBody()));
    }
}
