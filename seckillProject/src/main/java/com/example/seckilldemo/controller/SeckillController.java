package com.example.seckilldemo.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.seckilldemo.pojo.*;
import com.example.seckilldemo.rabbitmq.mqSender;
import com.example.seckilldemo.service.ITGoodsService;
import com.example.seckilldemo.service.ITOrderService;
import com.example.seckilldemo.service.ITSeckillGoodsService;
import com.example.seckilldemo.service.ITSeckillOrderService;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.RespBeanEnum;
import com.example.seckilldemo.vo.goodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    ITGoodsService goodsService;
    @Autowired
    ITOrderService orderService;
    @Autowired
    ITSeckillOrderService seckillOrderService;
    @Autowired
    ITSeckillGoodsService seckillGoodsService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    mqSender sender;

    Map<Long,Boolean> emptyMap=new HashMap<>();
    //QPS:4500,redis缓存+sql直连
    //QPS:6000,REDIS缓存与内存标记,rabbitmq
    @RequestMapping(value = "/doSeckill",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public RespBean doSeckill(Model model, TUser user, Long goodsId){
        if (user==null)
            return RespBean.error();

        TSeckillOrder seckillOrder =(TSeckillOrder)redisTemplate.opsForValue().get("order"+user.getId()+":"+goodsId);
        if (seckillOrder!=null){
            model.addAttribute("errmsg",RespBean.error().getMessage());
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        //判断库存
//        if (goodsById.getStockCount()<1){
//            model.addAttribute("errmsg",RespBean.error().getMessage());
//            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
//        }
        //内存标记，如果售空，无需查询redis
        if (emptyMap.get(goodsId))
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        Long currStock = redisTemplate.opsForValue().decrement("seckillGoods" + goodsId);
        if (currStock<0){
            emptyMap.put(goodsId,true);
            redisTemplate.opsForValue().increment("seckillGoods" + goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }else {
            sender.seckillSender(JSON.toJSONString(new SeckillMessage(user,goodsId)));
            return RespBean.success(0);
        }

////        //判断是否重复购买
////        TSeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<TSeckillOrder>().eq("user_id", user.getId()).eq
////                ("goods_id", goodsId));
//        TSeckillOrder seckillOrder =(TSeckillOrder)redisTemplate.opsForValue().get("order"+user.getId()+":"+goodsId);
//
//        if (seckillOrder!=null){
//            model.addAttribute("errmsg",RespBean.error().getMessage());
//            return RespBean.error();
//        }
//
//        TOrder order = orderService.seckill(user, goodsById);
//        return RespBean.success(order);

    }

    /*
    /轮询订单结果，如果订单下单成功返回订单号，失败返回-1.等待返回0
     */
    @GetMapping("/getResult")
    @ResponseBody
    public RespBean getOrder(TUser user,long goodsId){
        if (user==null){
            return  RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        long orderId=seckillOrderService.getResult(user,goodsId);
        return RespBean.success(orderId);
    }

    @Override
    //redis 初始化库存
    public void afterPropertiesSet() throws Exception {
        List<goodsVo> goods = goodsService.getGoods();
        if (goods==null)return;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        for (goodsVo temp:goods
             ) {
            emptyMap.put(temp.getId(),false);
            valueOperations.set("seckillGoods"+temp.getId(),temp.getStockCount());
        }
    }
}
