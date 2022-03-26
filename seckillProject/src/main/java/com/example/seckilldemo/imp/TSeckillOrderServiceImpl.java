package com.example.seckilldemo.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckilldemo.mapper.TSeckillOrderMapper;
import com.example.seckilldemo.pojo.TSeckillOrder;
import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.service.ITSeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */
@Service
public class TSeckillOrderServiceImpl extends ServiceImpl<TSeckillOrderMapper, TSeckillOrder> implements ITSeckillOrderService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    TSeckillOrderMapper seckillOrderMapper;

    @Override
    public long getResult(TUser user, long goodsId) {
        TSeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<TSeckillOrder>().eq("user_id",
                user.getId()).eq("goods_id", goodsId));
        if (seckillOrder!=null)
            return seckillOrder.getOrderId();
        else if (redisTemplate.hasKey("isStockEmpty:"+goodsId))
            return -1l;
        else
        return 0l;
    }
}
