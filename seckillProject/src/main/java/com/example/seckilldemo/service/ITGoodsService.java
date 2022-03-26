package com.example.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.mapper.TGoodsMapper;
import com.example.seckilldemo.pojo.TGoods;
import com.example.seckilldemo.vo.goodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */

public interface ITGoodsService extends IService<TGoods> {

    public List<goodsVo> getGoods();


    goodsVo getGoodsById(Long goodsId);
}
