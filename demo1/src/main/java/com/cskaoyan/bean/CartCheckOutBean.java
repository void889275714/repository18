package com.cskaoyan.bean;

import lombok.Data;

/**
 * @Description TODO
 * @Date 2020-01-02 14:52
 * @Created by ouyangfan
 */
@Data
public class CartCheckOutBean {
    Integer cartId;
    Integer addressId;
    Integer couponId;
    Integer grouponRulesId;
}
