package com.cskaoyan.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class WxCartIndex {

    Short goodsCount;

    Short checkedGoodsCount;

    BigDecimal goodsAmount;

    BigDecimal checkedGoodsAmount;

    public WxCartIndex(Short goodsCount, Short checkedGoodsCount, BigDecimal goodsAmount, BigDecimal checkedGoodsAmount) {
        this.goodsCount = goodsCount;
        this.checkedGoodsCount = checkedGoodsCount;
        this.goodsAmount = goodsAmount;
        this.checkedGoodsAmount = checkedGoodsAmount;
    }
}
