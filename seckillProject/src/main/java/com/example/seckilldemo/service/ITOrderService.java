package com.example.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.pojo.TOrder;
import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.vo.goodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */
public interface ITOrderService extends IService<TOrder> {
    public TOrder seckill(TUser user, goodsVo goods);
}
