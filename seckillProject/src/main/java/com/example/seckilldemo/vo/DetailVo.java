package com.example.seckilldemo.vo;

import com.example.seckilldemo.pojo.TUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private TUser user;
    private goodsVo goods;
    private int secKillStatus;
    private int remainSeconds;

}
