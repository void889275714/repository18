package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.OrderMapper;
import com.cskaoyan.mapper.Order_goodsMapper;
import com.cskaoyan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatUserServiceImpl implements   StatUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    Order_goodsMapper order_goodsMapper;
    @Autowired
    OrderMapper orderMapper;


    @Override
    /**
     * 用户统计
     */
    public Map queryStatUserMsg() {
        UserExample userExample = new UserExample();
        //查询用户总数
        long num = userMapper.countByExample(userExample);
        //查询用户增加时时间
        Date addBirthday = userMapper.queryStatUserBirthday(userExample);
        //将上述查询结果封装到bean中
        RowsMsg rowsMsg = new RowsMsg();
        rowsMsg.setDay(addBirthday);
        rowsMsg.setUsers((int) num);

        List<RowsMsg> rowsMsgList = new ArrayList();
        rowsMsgList.add(rowsMsg);
        //按照json格式封装
        List<String> msg = new ArrayList();
        msg.add("day");
        msg.add("users");

        //将所有的信息整合到map中
        HashMap Map = new HashMap();
        Map.put("columns",msg);
        Map.put("rows",rowsMsgList);
        return Map;



    }

    @Override
    /**
     * 商品统计
     */
    public Map queryStatGoodsMsg() {

        List<GoodsAllMsg> goodsAllMsgs = order_goodsMapper.selectGoodsAllMsg();
        List<String> msg = new ArrayList<>();
        msg.add("day");
        msg.add("orders");
        msg.add("products");
        msg.add("amount");
        Map map = new HashMap<>();
            map.put("columns",msg);
            map.put("rows",goodsAllMsgs);

        return map;
    }
}
