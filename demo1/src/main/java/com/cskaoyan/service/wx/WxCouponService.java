package com.cskaoyan.service.wx;

import java.util.Map;

public interface WxCouponService {
    Map<String,Object> showCouponList(int status,int page,int size);

    //假新增优惠券
    int createCoupon();

    //领取优惠券
    boolean receiveCoupon(int couponId);
}
