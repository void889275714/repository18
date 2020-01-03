package com.cskaoyan.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description TODO
 * @Date 2020-01-02 14:55
 * @Created by ouyangfan
 */
@Data
public class CartCheckOutForReturn {
    Integer addressId;
    Integer grouponRulesId;
    Integer couponId;
    Integer availableCouponLength;
    Address checkedAddress;
    List<Goods> checkedGoodsList;
    BigDecimal actualPrice;
    BigDecimal orderTotalPrice;
    BigDecimal couponPrice;
    BigDecimal freightPrice;
    BigDecimal goodsTotalPrice;
    BigDecimal grouponPrice;
}
