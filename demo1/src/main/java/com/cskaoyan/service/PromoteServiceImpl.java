package com.cskaoyan.service;

import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.AdMapper;
import com.cskaoyan.mapper.CouponMapper;
import com.cskaoyan.mapper.Coupon_userMapper;
import com.cskaoyan.mapper.topicMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PromoteServiceImpl implements PromoteService {

    @Autowired
    AdMapper adMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    Coupon_userMapper coupon_userMapper;

    @Autowired
    topicMapper topicmapper;

    /**
     * 优惠券管理
     * @param listCondition
     * @return
     */
    @Override
    public Map<String,Object> selectCouponLists(ListCondition listCondition) {
        //开启分页
        PageHelper.startPage(listCondition.getPage(),listCondition.getLimit());

        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        couponExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        //假删不显示
        criteria.andDeletedEqualTo(false);
        String name = listCondition.getName();
        int type = listCondition.getType();
        String status1 = listCondition.getStatus();
        if(status1 != null && !"".equals(status1)){
            criteria.andStatusEqualTo(Short.parseShort(status1));
        }
        if (name != null && name.length() > 0) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type >= 0) {
            criteria.andTypeEqualTo((short) type);
        }
        List<Coupon> couponLists = couponMapper.selectByExample(couponExample);
        long count = couponMapper.countByExample(couponExample);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",count);
        map.put("items",couponLists);
        return map;

    }

    @Override
    public Coupon createCoupon(Coupon coupon) {

        int insert = couponMapper.insertSelective(coupon);
        //id会直接返回到Bean中
        Integer id = coupon.getId();
        //Coupon coupon1 = couponMapper.selectByPrimaryKey(id);
        return coupon;

    }

    @Override
    public int deleteCoupon(Coupon coupon) {

        int delete = couponMapper.updateByPrimaryKeySelective(coupon);
        return delete;
    }

    @Override
    public Coupon updateCoupon(Coupon coupon) {

        int update = couponMapper.updateByPrimaryKeySelective(coupon);
        if (update == 1) {
            Coupon coupon1 = couponMapper.selectByPrimaryKey(coupon.getId());
            return coupon1;
        }
        return coupon;
    }

    //优惠券详情
    @Override
    public Map<String, Object> selectUsersByCouponId(ListCondition listCondition) {
        ArrayList<Object> arrayList = new ArrayList<>();
        Coupon_userExample example = new Coupon_userExample();
        int couponId = listCondition.getCouponId();
        String userId = listCondition.getUserId();
        String status = listCondition.getStatus();
        //查询couponId为传入的id的数据
        //添加条件
        Coupon_userExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andCouponIdEqualTo(couponId);
        if(userId != null && !"".equals(userId)){
            criteria.andUserIdEqualTo(Integer.parseInt(userId));
        }
        if (status != null && !"".equals(status)){
            criteria.andStatusEqualTo(Short.valueOf(status));
        }
        //查询coupon_user表信息
        List<Coupon_user> coupon_users = coupon_userMapper.selectByExample(example);
        //long total = coupon_userMapper.countByExample(new Coupon_userExample());
        Map<String, Object> map = new HashMap<>();
        map.put("total", coupon_users.size());
        map.put("items", coupon_users);
        return map;

    }

    @Override
    public Coupon selectCouponById(Integer id) {

        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        return coupon;

    }

    /**
     * 专题管理
     * @param listCondition
     * @return
     */
    @Override
    public Map<String, Object> selectTopices(ListCondition listCondition) {
        //开启分页
        PageHelper.startPage(listCondition.getPage(),listCondition.getLimit());
        //添加排序条件
        topicExample topicexample = new topicExample();
        topicexample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        topicExample.Criteria criteria = topicexample.createCriteria();
        criteria.andDeletedEqualTo(false);
        String title = listCondition.getTitle();
        String subtitle = listCondition.getSubtitle();
        if (title != null && title.length() > 0) {
            criteria.andTitleLike(("%" + title + "%"));
        }
        if (subtitle != null && subtitle.length() > 0) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        //拿到dao/mapper层返回的数据
        List<topic> topicLists = topicmapper.selectByExample(topicexample);
        long count = topicmapper.countByExample(topicexample);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",count);
        map.put("items",topicLists);
        return map;

    }

    @Override
    public int deleteTopic(topic toPic) {

        int delete = topicmapper.updateByPrimaryKeySelective(toPic);
        return delete;
    }

    @Override
    public topic updateTopic(topic toPic) {

        int update = topicmapper.updateByPrimaryKeySelective(toPic);
        if (update == 1) {
            topic toPic1 = topicmapper.selectByPrimaryKey(toPic.getId());
            return toPic1;
        }
        return toPic;
    }

    @Override
    public topic createTopic(topic toPic) {

        int insert = topicmapper.insertSelective(toPic);
        //id会直接返回到Bean中
        Integer id = toPic.getId();
        topic toPic1 = topicmapper.selectByPrimaryKey(id);
        return toPic1;

    }

    /**
     * 广告管理
     * @param listCondition
     * @return
     */
    @Override
    public Map<String,Object> selectAllAds(ListCondition listCondition) {
        //开启分页
        PageHelper.startPage(listCondition.getPage(),listCondition.getLimit());
        //添加排序条件
        AdExample adExample = new AdExample();
        adExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        AdExample.Criteria criteria = adExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        String content = listCondition.getContent();
        String name = listCondition.getName();
        if (content != null && content.length() > 0) {
            criteria.andContentLike("%" + content + "%");
        }
        if (name != null && name.length() > 0) {
            criteria.andNameLike("%" + name + "%");
        }
        //拿到dao/mapper层返回的数据
        List<Ad> adLists = adMapper.selectByExample(adExample);
        long count = adMapper.countByExample(adExample);
        HashMap<String, Object> map = new HashMap<>();
        map.put("items",adLists);
        map.put("total",count);
        return map;

    }

    @Override
    public Ad updateAd(Ad ad) {

        int update = adMapper.updateByPrimaryKeySelective(ad);
        if (update == 1) {
            Ad ad1 = adMapper.selectByPrimaryKey(ad.getId());
            return ad1;
        }
        return ad;

    }

    @Override
    public Ad createAd(Ad ad) {

        int insert = adMapper.insertSelective(ad);
        //id会直接返回到Bean中
        Integer id = ad.getId();
        Ad ad1 = adMapper.selectByPrimaryKey(id);
        return ad1;

    }

    @Override
    public int deleteAd(Ad ad) {

        int delete = adMapper.updateByPrimaryKeySelective(ad);
        return delete;
    }
}
