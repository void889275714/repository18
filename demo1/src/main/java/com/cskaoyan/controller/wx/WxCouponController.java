package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.CouponExchange;
import com.cskaoyan.service.wx.WxCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WxCouponController {

    @Autowired
    WxCouponService wxCouponService;


    /**
     * 显示用户优惠券
     * @param status
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("wx/coupon/mylist")
    public BaseRespVo showMyCouponList(int status, int page, int size){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String, Object> map = wxCouponService.showCouponList(status, page, size);
        baseRespVo.setData(map);
        return baseRespVo;
    }


    /**
     * 优惠券兑换
     * @return
     */
    @RequestMapping("wx/coupon/exchange")
    public String exchange(@RequestBody CouponExchange couponExchange) {
        String result = "{\"errno\":742,\"errmsg\":\"优惠券不正确\"}";
        if ("CA05F8B2".equals(couponExchange.getCode())) {
            int coupon = wxCouponService.createCoupon();
            if (coupon > 0) {
                result = "{\"errno\":0,\"errmsg\":\"优惠券兑换成功！\"}";
            }
        }
        return result;
        }


    /**
     * 主页优惠券领取
      */
    @RequestMapping("wx/coupon/receive")
    public BaseRespVo couponReceive(@RequestBody CouponExchange couponExchange){
        int couponId = couponExchange.getCouponId();
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        boolean flag = wxCouponService.receiveCoupon(couponId);
        if (flag) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }else {
            baseRespVo.setErrno(740);
            baseRespVo.setErrmsg("优惠券领取失败或您已经领取！");
        }
        return baseRespVo;
    }


}
