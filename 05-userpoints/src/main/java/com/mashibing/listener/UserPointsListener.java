package com.mashibing.listener;

import com.mashibing.config.RabbitMQConfig;
import com.mashibing.service.UserPointsConsume;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPointsListener {
    @Autowired
    private UserPointsConsume userPointsConsume;

    @RabbitListener(queues={RabbitMQConfig.USER_POINTS_QUEUE})
    public void consume(String msg, Channel channel, Message message) throws Exception {
        userPointsConsume.consume(message,msg);
        //手动ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
