package com.cskaoyan.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MyGrouponWxB {
    private Integer orderId;
    private String orderSn;
    private BigDecimal actualPrice;
    private Integer joinerCount;
    private List goodsList;
    private GrouponRulsWX rules;
}
