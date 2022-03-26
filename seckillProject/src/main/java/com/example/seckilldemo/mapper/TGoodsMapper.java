package com.example.seckilldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckilldemo.pojo.TGoods;
import com.example.seckilldemo.vo.goodsVo;
import org.apache.ibatis.annotations.Mapper;

;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */
@Mapper
public interface TGoodsMapper extends BaseMapper<TGoods> {
    List<goodsVo> getGoodsVo();

    goodsVo getGoodsVoById(Long goodsId);
}
