package com.cskaoyan.service.promote;

import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.promote.Ad;
import com.cskaoyan.bean.promote.Coupon;
import com.cskaoyan.bean.promote.CouponExample;
import com.cskaoyan.bean.promote.topic;

import java.util.List;
import java.util.Map;

public interface PromoteService {

    /**
     * 广告管理
     * @param listCondition
     * @return
     */

    //显示和模糊查询
    Map<String,Object> selectAllAds(ListCondition listCondition);
    //更新
    Ad updateAd(Ad ad);
    //创建
    Ad createAd(Ad ad);
    //删除
    int deleteAd(Ad ad);

    /**
     * 优惠券管理
     * @param listCondition
     * @return
     */

    //显示和模糊查询
    Map<String,Object> selectCouponLists(ListCondition listCondition);
    //创建
    Coupon createCoupon(Coupon coupon);
    //删除
    int deleteCoupon(Coupon coupon);
    //更新
    Coupon updateCoupon(Coupon coupon);
    //详情显示下方
    Map<String,Object> selectUsersByCouponId(ListCondition listCondition);
    //详情显示上方
    Coupon selectCouponById(Integer id);

    /**
     * 专题管理
     */

    //查询和显示
    Map<String,Object> selectTopices(ListCondition listCondition);
    //删除
    int deleteTopic(topic toPic);
    //更新
    topic updateTopic(topic toPic);
    //增加
    topic createTopic(topic toPic);

}
