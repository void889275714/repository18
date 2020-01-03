package com.cskaoyan.service.wx;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.CouponMapper;
import com.cskaoyan.mapper.Coupon_userMapper;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WxCouponServiceImpl implements WxCouponService{

    @Autowired
    Coupon_userMapper coupon_userMapper;

    @Autowired
    CouponMapper couponMapper;

    /**
     * 个人中心 --> 显示个人优惠券
     * @param status
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map<String, Object> showCouponList(int status, int page, int size) {
        PageHelper.startPage(page,size);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer id = user.getId();

        Coupon_userExample coupon_userExample = new Coupon_userExample();
        coupon_userExample.createCriteria().andUserIdEqualTo(id);
        //拿到登录的这个用户的优惠券信息
        List<Coupon_user> coupon_users = coupon_userMapper.selectByExample(coupon_userExample);

        List<Coupon> couponList = new ArrayList<>();
        for (Coupon_user coupon_user : coupon_users) {
            Integer couponId = coupon_user.getCouponId();
            CouponExample couponExample = new CouponExample();
            couponExample.createCriteria().andIdEqualTo(couponId);
            List<Coupon> coupons = couponMapper.selectByExample(couponExample);
            //拿到优惠券信息
            Coupon coupon = coupons.get(0);
            //对返回的数据进行封装
            Coupon coupon1 = new Coupon();
            coupon1.setId(coupon.getId());
            coupon1.setName(coupon.getName());
            coupon1.setDesc(coupon.getDesc());
            coupon1.setTag(coupon.getTag());
            coupon1.setMin(coupon.getMin());
            coupon1.setDiscount(coupon.getDiscount());
            coupon1.setStartTime(coupon_user.getStartTime());
            coupon1.setEndTime(coupon_user.getEndTime());

            couponList.add(coupon1);
        }

        int count = couponList.size();
        Map<String, Object> map = new HashMap<>();
        map.put("count",count);
        map.put("data",couponList);
        return map;
    }

    /**
     * 假新增优惠券
     * @return
     */
    @Override
    public int createCoupon() {
//        Coupon coupon1 = new Coupon();
//        coupon1.setId(1);
//        coupon1.setName("王道超级会员注册领");
//        coupon1.setDesc("拥有松哥签名照一张");
//        coupon1.setTag(null);
//        coupon1.setMin(new BigDecimal(20.20));
//        coupon1.setDiscount(new BigDecimal(9.99));
//        Date date = new Date();
//        coupon1.setStartTime(date);
//        String endTime = "2020-12-31 12:20:20";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Date parse = simpleDateFormat.parse(endTime);
//            coupon1.setEndTime(parse);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        Coupon_user coupon_user = new Coupon_user();
        //先固定写了
        coupon_user.setUserId(3);
        coupon_user.setCouponId(11);
        coupon_user.setStatus((short) 0);
        coupon_user.setUsedTime(new Date());
        coupon_user.setStartTime(new Date());
        String endTime = "2020-12-31 12:20:20";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(endTime);
            coupon_user.setEndTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        coupon_user.setOrderId(1);

        int insert = coupon_userMapper.insert(coupon_user);
        return insert;
    }



    /**
     * 主页领取优惠券
     * @param couponId
     * @return
     */
    @Override
    public boolean receiveCoupon(int couponId) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        Integer id = user.getId();
        //如果数据库里有此id 的此 couponId 则返回 false, 如果没有则插入 coupon_user 表中数据，返回true
        Coupon_userExample coupon_userExample = new Coupon_userExample();
        coupon_userExample.createCriteria().andUserIdEqualTo(id).andCouponIdEqualTo(couponId);

        List<Coupon_user> coupon_users = coupon_userMapper.selectByExample(coupon_userExample);
        if (coupon_users.size() > 0) {
            return false;
        }
        //如果这个用户没领取这个优惠券
        Coupon_user coupon_user = new Coupon_user();
        coupon_user.setUserId(id);
        coupon_user.setCouponId(couponId);
        coupon_user.setStatus((short) 0);

        //下单付款的时间,这里先存一个null 不知道可以不
        coupon_user.setUsedTime(null);
        coupon_user.setStartTime(new Date());
        String endTime = "2020-12-31 12:20:20";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(endTime);
            coupon_user.setEndTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        coupon_user.setOrderId(1);
        coupon_user.setAddTime(new Date());
        coupon_user.setUpdateTime(new Date());

        int insert = coupon_userMapper.insert(coupon_user);
        if (insert > 0) {
            return true;
        }
        return false;
    }
}
