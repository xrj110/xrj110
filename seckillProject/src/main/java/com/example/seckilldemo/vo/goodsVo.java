package com.example.seckilldemo.vo;

import com.example.seckilldemo.pojo.TGoods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

public class goodsVo extends TGoods {

    private BigDecimal seckillPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
