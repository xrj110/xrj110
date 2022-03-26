package com.example.seckilldemo.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author tony
 * @since 2022-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TSeckillOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long orderId;

    private Long goodsId;


}
