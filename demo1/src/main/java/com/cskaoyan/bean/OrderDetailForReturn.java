package com.cskaoyan.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description TODO
 * @Date 2020-01-01 21:24
 * @Created by ouyangfan
 */
@Data
public class OrderDetailForReturn {
    Integer id;
    List<Order_goods> goodsList;
    BigDecimal actualPrice;
    String orderSn;
    boolean isGroupin;
    String orderStatusText;
    HandleOption handleOption;
}
