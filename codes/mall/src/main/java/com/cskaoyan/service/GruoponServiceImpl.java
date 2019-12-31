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
    @Autowired
    GrouponMapper grouponMapper;




    /**
     * 推广管理的团购活动的显示和搜索功能
     * @param listCondition
     * @return
     */
    @Override
    public Map queryActivity(ListCondition listCondition) {
        //获取参数中的分页变量和分页条数限制，开启分页
        PageHelper.startPage(listCondition.getPage(),listCondition.getLimit());

        //调用逆向工程的example
        GrouponExample grouponExample = new GrouponExample();
        GrouponExample.Criteria criteria = grouponExample.createCriteria();

        // 对查询条件进行判断
        String goodsId = listCondition.getGoodsId();
        if (goodsId != null && goodsId.length() != 0 && goodsId != "") {
            criteria.andIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);
        // 根据参数中的条件进行排序
        grouponExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        // 查询出总条目数
        List<Groupon> list = grouponMapper.selectByExample(grouponExample);
        PageInfo<Groupon> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",list);
        return map;
    }
}
