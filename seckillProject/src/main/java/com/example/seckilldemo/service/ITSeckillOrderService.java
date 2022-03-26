package com.example.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.pojo.TSeckillOrder;
import com.example.seckilldemo.pojo.TUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */
public interface ITSeckillOrderService extends IService<TSeckillOrder> {

    long getResult(TUser user, long goodsId);
}
