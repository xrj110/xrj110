package com.example.seckilldemo.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.example.seckilldemo.pojo.SeckillMessage;
import com.example.seckilldemo.pojo.TSeckillOrder;
import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.service.ITGoodsService;

import com.example.seckilldemo.service.ITOrderService;
import com.example.seckilldemo.vo.goodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class mqReceiver {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ITGoodsService goodsService;
    @Autowired
    ITOrderService orderService;

    @RabbitListener(queues = "seckill_queue")
    public void Receiver(String msg){
        SeckillMessage seckillMessage = JSON.parseObject(msg, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodsId();
        TUser user = seckillMessage.getUser();
        log.info("收到信息"+seckillMessage.toString());
                TSeckillOrder seckillOrder =(TSeckillOrder)redisTemplate.opsForValue().get("order"+ user.getId()
                        +":"+ goodsId);
        if (seckillOrder!=null){
           return;
        }
        goodsVo goodsById = goodsService.getGoodsById(goodsId);
        //判断库存
        if (goodsById.getStockCount()<1){
            return;
        }
        //生成订单
        orderService.seckill(user,goodsById);
        return;
    }
}
