package com.example.seckilldemo.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckilldemo.mapper.TGoodsMapper;
import com.example.seckilldemo.pojo.TGoods;
import com.example.seckilldemo.service.ITGoodsService;
import com.example.seckilldemo.vo.goodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */
@Service
public class TGoodsServiceImpl extends ServiceImpl<TGoodsMapper, TGoods> implements ITGoodsService {
    @Autowired
    TGoodsMapper goodsMapper;
    @Override
    public List<goodsVo> getGoods() {
        return goodsMapper.getGoodsVo();
    }

    @Override
    public goodsVo getGoodsById(Long goodsId) {
        return goodsMapper.getGoodsVoById(goodsId);
    }

}
