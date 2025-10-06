package com.mashibing.listener;

import com.mashibing.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BusinessListener {
    @RabbitListener(queues={RabbitMQConfig.BUSINESS_QUEUE})
    public void consume(String msg, Channel channel, Message message) throws Exception {
        //预扣除优惠劵
        Thread.sleep(400);
        System.out.println("通知商家成功"+msg);
        //手动ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
