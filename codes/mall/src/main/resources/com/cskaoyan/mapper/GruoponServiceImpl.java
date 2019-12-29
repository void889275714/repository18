package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.GrouponMapper;
import com.cskaoyan.mapper.Groupon_rulesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bruce
 */
@Service
public class GruoponServiceImpl implements GruoponService {

    @Autowired
    Groupon_rulesMapper groupon_rulesMapper;
    @Autowired
    GoodsMapper goodsMapper;


    @Override
    public Groupon_rules addRules(GrouponReceive grouponReceive) {
        return null;
    }

    @Override
    public boolean queryGoodsId(String goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(Integer.valueOf(goodsId));
        if (goods != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 推广管理的团购活动的显示和搜索功能
     * @param listCondition
     * @return
     */
    @Override
    public Map queryActivity(ListCondition listCondition) {
        PageHelper.startPage(listCondition.getPage(),listCondition.getLimit());
        Groupon_rulesExample groupon_rulesExample = new Groupon_rulesExample();
        Groupon_rulesExample.Criteria criteria = groupon_rulesExample.createCriteria();
        String goodsId = listCondition.getGoodsId();
        if (goodsId != null && goodsId.length() != 0 && goodsId != "") {
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        groupon_rulesExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Groupon_rules> list = groupon_rulesMapper.selectByExample(groupon_rulesExample);
        PageInfo<Groupon_rules> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",list);
        return map;
    }
}
