package com.example.seckilldemo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class mqSender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void seckillSender(String msg){
       rabbitTemplate.convertAndSend("seckill_exchange","seckill.message",msg);
       log.info("成功发送消息："+msg);
    }
}
