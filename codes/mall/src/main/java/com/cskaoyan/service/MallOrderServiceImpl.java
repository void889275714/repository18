package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bruce-g
 */
@Service
public class MallOrderServiceImpl implements MallOrderService {

    @Autowired
    SelectOrderMapper selectByExample;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    Order_goodsMapper orderGoodsMapper;
    @Autowired
    Goods_specificationMapper goods_specificationMapper;

    /**
     * 订单的展示和搜索功能
     *
     * @param listCondition
     * @return
     */
    @Override
    public Map selectOrder(ListCondition listCondition) {
        //获得分页的条件，开启分页
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());

        // 获得需要查询订单的bean（逆向工程生成的）
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();

        // 把需要查询的userID 和 orderSn 和 order status array 取出来，这是可以查询的三个条件框
        String userId = listCondition.getUserId();
        String orderSn = listCondition.getOrderSn();
        List<Short> orderStatusArray = listCondition.getOrderStatusArray();

        //对条件进行判断
        if (userId != null && userId.length() != 0 && userId != "") {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
       }

        if (orderSn != null && orderSn != "" && orderSn.length() != 0) {
            criteria.andOrderSnEqualTo(orderSn);
        }
        if (orderStatusArray != null) {
            criteria.andOrderStatusIn(orderStatusArray);
        }

        //  取得两个参数拼接，是查询的条件，例如根据添加时间降序排序
        orderExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Order> orderList = orderMapper.selectByExample(orderExample);

        //计算查询到的总数
        PageInfo<Order> orderPageInfo = new PageInfo<>(orderList);
        long total = orderPageInfo.getTotal();

        //放入map中
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", total);
        hashMap.put("items", orderList);
        return hashMap;
    }

    /**
     * 展示订单详情的功能
     * @param id
     * @return
     */
    @Override
    public Map queryOrderPage(int id) {
        // 获得goods example对象，使用逆向工程的xml
        Order_goodsExample goodsExample = new Order_goodsExample();
        Order_goodsExample.Criteria criteria = goodsExample.createCriteria();

        // 添加查询条件
        criteria.andOrderIdEqualTo(id);

        /**
         * 查order表的数据
         */
        Order order = orderMapper.selectByPrimaryKey(id);
        /**
         * 查user表的数据
         */
        Integer userId = order.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        String avatar = user.getAvatar();
        String nickname = user.getNickname();
        String[] users = {avatar,nickname};

        /**
         * 查order_goods表的数据
         */
        List<Order_goods> order_goods = orderGoodsMapper.selectByExample(goodsExample);

        //放入map中
        HashMap hashMap = new HashMap();
        hashMap.put("ordergoods",order_goods);
        hashMap.put("user",users);
        hashMap.put("order",order);
        return hashMap;
    }
}
