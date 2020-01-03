package com.cskaoyan.service;

import com.cskaoyan.bean.GoodsExample;
import com.cskaoyan.bean.GoodsProductExample;
import com.cskaoyan.bean.OrderExample;
import com.cskaoyan.bean.UserExample;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.GoodsProductMapper;
import com.cskaoyan.mapper.OrderMapper;
import com.cskaoyan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2019-12-28 15:06
 * @Created by ouyangfan
 */
@Service
public class DashboardServiceImpl implements DashboardService{
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public Map queryDashboardInfo() {
        //查询商品总数
        long goodsTotal = goodsMapper.countByExample(new GoodsExample());
        //查询订单总数
        long orderTotal = orderMapper.countByExample(new OrderExample());
        //查询货品总数
        long productTotal = goodsProductMapper.countByExample(new GoodsProductExample());
        //查询用户总数
        long userTotal = userMapper.countByExample(new UserExample());
        //将结果封装
        Map<String, Integer> map = new HashMap<>();
        map.put("goodsTotal", (int)goodsTotal);
        map.put("orderTotal", (int)orderTotal);
        map.put("productTotal", (int)productTotal);
        map.put("userTotal", (int)userTotal);
        return map;
    }
}
