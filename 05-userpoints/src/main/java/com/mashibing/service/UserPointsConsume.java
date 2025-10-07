package com.mashibing.service;

import org.springframework.amqp.core.Message;

public interface UserPointsConsume {
    void consume(Message message,String msg);
}
