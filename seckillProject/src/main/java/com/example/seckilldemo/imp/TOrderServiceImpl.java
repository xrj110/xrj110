package com.example.seckilldemo.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckilldemo.mapper.TOrderMapper;
import com.example.seckilldemo.pojo.TOrder;
import com.example.seckilldemo.pojo.TSeckillGoods;
import com.example.seckilldemo.pojo.TSeckillOrder;
import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.service.ITOrderService;
import com.example.seckilldemo.service.ITSeckillGoodsService;
import com.example.seckilldemo.service.ITSeckillOrderService;
import com.example.seckilldemo.vo.goodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements ITOrderService {
    @Autowired
    private ITSeckillGoodsService seckillGoodsService;
    @Autowired
    private TOrderMapper orderMapper;
    @Autowired
    private ITSeckillOrderService seckillOrderService;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    @Transactional
    public TOrder seckill(TUser user, goodsVo goods) {

        boolean result = seckillGoodsService.update(new UpdateWrapper<TSeckillGoods>().
                setSql("stock_count=" + "stock_count-1").eq("goods_id", goods.getId()).gt("stock_count", 0));
        if (!result){
            redisTemplate.opsForValue().set("isStockEmpty"+goods.getId(),"true");
            return null;
        }
        TSeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<TSeckillGoods>().eq("goods_id", goods.getId()));
//        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
//        seckillGoodsService.updateById(seckillGoods);
        //generate  order
        TOrder order = new TOrder();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setCreateData(new Date());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setStatus(0);
        order.setOrderChannel(1);
        orderMapper.insert(order);
        //generate seckill order
        TSeckillOrder seckillOrder = new TSeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillGoods.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order"+user.getId()+":"+goods.getId(),seckillOrder);
        return order;
    }
}
