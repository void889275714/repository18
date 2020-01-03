package com.cskaoyan.bean;

import lombok.Data;

/**
 * @Description TODO
 * @Date 2020-01-02 17:38
 * @Created by ouyangfan
 */
@Data
public class OrderSubmit {
    Integer addressId;
    Integer cartId;
    Integer couponId;
    Integer grouponLinkId;
    Integer grouponRulesId;
    String message;
}
