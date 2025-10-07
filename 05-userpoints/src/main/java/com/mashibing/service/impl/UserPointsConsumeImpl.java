package com.mashibing.service.impl;

import com.mashibing.mapper.UserPointsIdempotentMapper;
import com.mashibing.service.UserPointsConsume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserPointsConsumeImpl implements UserPointsConsume {
    @Resource
    private UserPointsIdempotentMapper userPointsIdempotentMapper;

    private final String ID_NAME = "spring_returned_message_correlation";

    @Override
    @Transactional
    public void consume(Message message,String msg) {
        //获取生产者提供的CorrelationId 基于Header去获取
        String id = message.getMessageProperties().getHeader(ID_NAME);
        //查询幂等表是否存在当前的消息标识
        int count = userPointsIdempotentMapper.findById(id);
        //如果存在则直接return 结束
        if(count ==1){
            log.info("消息已经被消费！无需重复消费");
            return;
        }
        //如果不存在，插入消息到幂等表
        userPointsIdempotentMapper.save(id);
        //执行消费逻辑，预扣除优惠劵
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("扣除用户积分成功！！！"+msg);

    }
}
