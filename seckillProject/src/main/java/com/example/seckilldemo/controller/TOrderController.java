package com.example.seckilldemo.controller;


import com.example.seckilldemo.mapper.TGoodsMapper;
import com.example.seckilldemo.mapper.TOrderMapper;
import com.example.seckilldemo.mapper.TSeckillOrderMapper;
import com.example.seckilldemo.pojo.TGoods;
import com.example.seckilldemo.pojo.TOrder;
import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.vo.OrderVo;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.goodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/Order")
public class TOrderController {
    //渲染订单页面数据
    @Autowired
    TOrderMapper orderMapper;
    @Autowired
    TSeckillOrderMapper seckillOrderMapper;
    @Autowired
    TGoodsMapper goodsMapper;
    @RequestMapping("/detail")
    public RespBean getDetail(TUser user,Long orderId){
        if (user==null)
            return RespBean.error();
        if (orderId==null)
            return RespBean.error();
        TOrder order = orderMapper.selectById(orderId);
        OrderVo orderVo=new OrderVo();
        orderVo.setUser(user);
        orderVo.setOrder(order);
        goodsVo goods = goodsMapper.getGoodsVoById(order.getGoodsId());
        orderVo.setGoodsImg(goods.getGoodsImg());
        return RespBean.success(orderVo);
    }

}
