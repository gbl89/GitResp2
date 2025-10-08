package com.mashibing.listener;

import com.mashibing.config.RabbitMQConfig;
import com.mashibing.service.TbOrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DelayMessageListener {
    @Autowired
    private TbOrderService orderService;

    @RabbitListener(queues= RabbitMQConfig.DEAD_QUEUE)
    public void consume(String id, Channel channel, Message message) throws IOException {
        //调用Service实现订单状态的处理
        orderService.delayCancelOrder(id);

        //ack的提交
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
