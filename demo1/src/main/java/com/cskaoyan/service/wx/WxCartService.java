package com.cskaoyan.service.wx;

import com.cskaoyan.bean.*;

import java.util.Map;

public interface WxCartService {
    Map showIndex();

    int queryAddCart(WxAddCart wxAddCart);

    int queryGoodsCount();

    void update(Cart cart);

    Map delete(WxProductId wxProductId);

    Map checkedStatus(WxProductId wxProductId);

    int fastOrder(WxAddCart wxFastAdd);

    CartCheckOutForReturn cartCheckOut(CartCheckOutBean checkOutBean);
}
