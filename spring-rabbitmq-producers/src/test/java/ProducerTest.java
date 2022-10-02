/*
@author YG
@create 2022/10/2   23:31
*/


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 1.发送消息到默认交换机
     */
    @Test
    public void testHelloWorld(){
        rabbitTemplate.convertAndSend("spring_queue","hello world Default02 ...");
    }

    /**
     * 2.发送消息到Fanout类型交换机
     */
    @Test
    public void testFanout(){
        rabbitTemplate.convertAndSend("spring_fanout_exchange", "", "hello world Fanout ...");
    }

    /**
     * 3.发送消息到Topic类型交换机
     */
    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend("spring_topic_exchange", "heima.hehe.haha ...", "hello world Topic ...");
    }

}
