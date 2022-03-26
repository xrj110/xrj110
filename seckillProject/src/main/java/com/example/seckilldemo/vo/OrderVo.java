package com.example.seckilldemo.vo;

import com.example.seckilldemo.pojo.TOrder;
import com.example.seckilldemo.pojo.TUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {
    TUser user;
    private TOrder order;
    private String goodsImg;

}
