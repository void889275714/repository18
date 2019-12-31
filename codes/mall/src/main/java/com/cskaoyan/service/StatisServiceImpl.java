package com.cskaoyan.service;

import com.cskaoyan.bean.StatisOrder;
import com.cskaoyan.mapper.StatisOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisServiceImpl implements StatisService {

    @Autowired
    StatisOrderMapper statisOrderMapper;

    @Override
    public Map<Object,Object> selectStatisOrder() {

        List<StatisOrder> statisOrders = statisOrderMapper.statisOrder();
        String[] column = {"day","orders","customers","amount","pcr"};
        HashMap<Object, Object> map = new HashMap<>();
        map.put("columns",column);
        map.put("rows",statisOrders);
        return map;

    }
}
