package com.example.seckilldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckilldemo.pojo.TOrder;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */
public interface TOrderMapper extends BaseMapper<TOrder> {
    public TOrder getOrderById(long orderId);
}
