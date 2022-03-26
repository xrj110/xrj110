package com.example.seckilldemo.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitmqConfig {
    private static final String SECKILL_QUEUE="seckill_queue";
    private static final String SECKILL_EXCHANGE="seckill_exchange";
    @Bean
    public Queue queue(){
        return new Queue (SECKILL_QUEUE);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(SECKILL_EXCHANGE);
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind( queue()).to(exchange()).with("seckill.#");
    }


}
