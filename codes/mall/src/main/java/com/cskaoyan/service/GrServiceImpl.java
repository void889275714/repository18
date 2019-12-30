package com.cskaoyan.service;

import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.Groupon_rules;
import com.cskaoyan.bean.Groupon_rulesExample;
import com.cskaoyan.bean.CreateGrMsg;
import com.cskaoyan.bean.ListGrCondition;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.Groupon_rulesMapper;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrServiceImpl implements GrService{
    @Autowired
    Groupon_rulesMapper grouponRulesMapper;
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public Map queryGrMsg(ListGrCondition listGrCondition) {
        Groupon_rulesExample groupon_rulesExample = new Groupon_rulesExample();
        Groupon_rulesExample.Criteria criteria = groupon_rulesExample.createCriteria();
        //开启分页
        PageHelper.startPage(listGrCondition.getPage(),listGrCondition.getLimit());
        //添加排序条件
        groupon_rulesExample.setOrderByClause(listGrCondition.getSort()+ " "+listGrCondition.getOrder());

        //查询功能
        Integer goodsId= listGrCondition.getGoodsId();
        //当查询的条件不为null时，添加查询条件
        if(goodsId!=null && goodsId>0){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        List<Groupon_rules> grouponRules = grouponRulesMapper.selectByExample(groupon_rulesExample);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",grouponRulesMapper.countByExample(groupon_rulesExample));
        map.put("items",grouponRules);
        return map;
    }

    @Override
    public Groupon_rules insertGrMsg(CreateGrMsg createGrMsg) {
        Groupon_rules grouponRules = new Groupon_rules();

        int goodsId = createGrMsg.getGoodsId();
        //1.先从商品表获得对应的信息
          Goods goods=goodsMapper.selectByPrimaryKey(goodsId);
          //2. 将商品信息和请求参数传入规则表
        grouponRules.setGoodsId(createGrMsg.getGoodsId());
        grouponRules.setDiscountMember(createGrMsg.getDiscountMember());
        grouponRules.setDiscount(createGrMsg.getDiscount());
        grouponRules.setExpireTime(createGrMsg.getExpireTime());
        grouponRules.setAddTime( new Date());
        grouponRules.setUpdateTime(new Date());
        grouponRules.setGoodsName(goods.getName());
        grouponRules.setPicUrl(goods.getPicUrl());

         grouponRulesMapper.insertSelective(grouponRules);
        return grouponRules;
    }

    @Override
    public boolean deleteGrMsg(Groupon_rules groupon_rules) {

        int i = grouponRulesMapper.deleteByPrimaryKey(groupon_rules.getId());

        if(i>0){
            return  true;
        }
        return false;
    }

    @Override
    public boolean updateGrmsg(Groupon_rules groupon_rules) {

        Groupon_rules grouponRules2 = new Groupon_rules();
        grouponRules2=groupon_rules;

        int i = grouponRulesMapper.updateByPrimaryKeySelective(grouponRules2);

        if(i>0){
            return true;
        }
        return false;

    }
}
