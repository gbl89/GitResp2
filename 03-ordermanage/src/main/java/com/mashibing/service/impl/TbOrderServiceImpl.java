package com.mashibing.service.impl;

import com.mashibing.config.RabbitMQConfig;
import com.mashibing.mapper.TbOrderMapper;
import com.mashibing.service.TbOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;
@Service
@Slf4j
public class TbOrderServiceImpl implements TbOrderService {

    @Resource
    private TbOrderMapper orderMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public void save() {
        String id = UUID.randomUUID().toString();
        //创建订单
        orderMapper.save(id);
        //将消息发送到RabbitMQ中
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, "", id, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置生存时间为15秒
                message.getMessageProperties().setExpiration("15000");
                return message;
            }
        });
    }

    @Override
    @Transactional
    public void delayCancelOrder(String id) {
        //基于id 查询订单的信息 for update 锁定该行代码
        int orderState=orderMapper.findOrderStateByIdForUpdate(id);
        //判断该订单的状态
        if(orderState != 0){
            log.info("订单已经支付");
            return;
        }
        //修改订单状态
        log.info("订单未支付,修改订单状态为已取消");
        orderMapper.updateOrderStateById(-1,id);
    }
}
