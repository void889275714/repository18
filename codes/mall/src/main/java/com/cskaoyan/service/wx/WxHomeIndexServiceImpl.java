package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxHomeIndexServiceImpl implements WxHomeIndexService{

    @Autowired
    AdMapper adMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    CateGoryMapper cateGoryMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    GrouponMapper grouponMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    topicMapper topicMapper;


    @Override
    public Map queryIndex() {
        //得到banner的list
        List<Ad> adList = adMapper.selectByExample(new AdExample());
        //得到brandList
        List<Brand> brands = brandMapper.selectByExample(new BrandExample());
        //得到categories（分类）  channnel
        CateGoryExample cateGoryExample = new CateGoryExample();
        cateGoryExample.createCriteria().andPidEqualTo(0);
        List<CateGory> cateGoryList = cateGoryMapper.selectByExample(cateGoryExample);
        //获得优惠券list
        List<Coupon> coupons = couponMapper.selectByExample(new CouponExample());
        //获得团购的list
        List<Groupon> groupons = grouponMapper.selectByExample(new GrouponExample());
        //获得hotGoodList
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIsHotEqualTo(true);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        //获得新品首发
        GoodsExample goodsExample2 = new GoodsExample();
        goodsExample.createCriteria().andIsNewEqualTo(true);
        List<Goods> newList = goodsMapper.selectByExample(goodsExample2);
        //获得人气商品
        List<topic> topics = topicMapper.selectByExample(new topicExample());
        //自己构造floor goodsList
        int i1 = 1005001;
        String name1 = "餐厨";
        GoodsExample goodsExample1 = new GoodsExample();
        //查询goods表根据category-id
        goodsExample1.createCriteria().andCategoryIdEqualTo(i1);
        List<Goods> goodsList1 = goodsMapper.selectByExample(goodsExample1);

        //载构造一个list
        String name2 = "配件";
        int i2 = 1008000;
        GoodsExample goodsExample3 = new GoodsExample();
        goodsExample3.createCriteria().andCategoryIdEqualTo(i2);
        List<Goods> goodsList2 = goodsMapper.selectByExample(goodsExample3);
        //把得到的值放入map中
        HashMap<String, Object> map1 = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        map1.put("goodsList",goodsList1);
        map1.put("id",i1);
        map1.put("name",name1);
        map2.put("goodsList",goodsList2);
        map2.put("id",i2);
        map2.put("name",name2);

        //再放入list中
        ArrayList arrayList = new ArrayList();
        arrayList.add(map1);
        arrayList.add(map2);

        //把得到的数据放回map中
        HashMap<String, Object> map = new HashMap<>();
        map.put("banner",adList);
        map.put("brandList",brands);
        map.put("channel",cateGoryList);
        map.put("couponList",coupons);
        map.put("floorGoodsList",arrayList);
        map.put("grouponList",groupons);
        map.put("hotGoodsList",goodsList);
        map.put("newGoodsList",newList);
        map.put("topicList",topics);
        return map;
    }
}
