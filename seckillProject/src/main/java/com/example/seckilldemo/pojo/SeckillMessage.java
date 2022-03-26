package com.example.seckilldemo.pojo;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {
    private TUser user;
    private Long goodsId;
}
