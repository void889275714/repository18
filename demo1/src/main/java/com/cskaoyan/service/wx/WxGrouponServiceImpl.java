package com.cskaoyan.service.wx;

import com.cskaoyan.bean.*;

import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxGrouponServiceImpl implements WxGrouponService {
    @Autowired
    GrouponMapper grouponMapper;
    @Autowired
    Groupon_rulesMapper groupon_rulesMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    WxMyGrouponMapper wxMyGrouponMapper;


    @Override
    public Map queryWxGrouponMsg(ListWxCondition listWxCondition) {
        //分页
        int size = listWxCondition.getSize();
        int page = listWxCondition.getPage();
        PageHelper.startPage(page, size);

        Groupon_rulesExample groupon_rulesExample = new Groupon_rulesExample();
        Map map = new HashMap();
        //先查询团购规则表的信息
        List<Groupon_rules> grouponRules = groupon_rulesMapper.selectByExample(groupon_rulesExample);

        List<WxNeedMsg> wxNeedMsgs = new ArrayList<>();
        for (Groupon_rules gr : grouponRules) {
            //拿规则表的商品id查商品表
            WxGrouponMsg wxGrouponMsg = goodsMapper.selectByGoodId(gr.getGoodsId());
            //再连表查询，查出gouponprice
            WxNeedMsg wxNeedMsg = goodsMapper.AfterSelectByGoodId(gr.getId());
            //封装信息
            GoodsGrouponWx goodsGrouponWx = new GoodsGrouponWx();
            goodsGrouponWx.setBrief(wxGrouponMsg.getBrief());
            goodsGrouponWx.setCounterPrice(wxGrouponMsg.getCounterPrice());
            goodsGrouponWx.setId(wxGrouponMsg.getId());
            goodsGrouponWx.setPicUrl(wxGrouponMsg.getPicUrl());
            goodsGrouponWx.setName(wxGrouponMsg.getName());
            goodsGrouponWx.setRetailPrice(wxGrouponMsg.getRetailPrice());

            wxNeedMsg.setGoods(goodsGrouponWx);
            //加进列表
            wxNeedMsgs.add(wxNeedMsg);
        }

        map.put("data", wxNeedMsgs);
        // map.put("count",(int)groupon_rulesMapper.countByExample(groupon_rulesExample));
        map.put("count", grouponRules.size());
        return map;
    }

    @Override
    public Map queryMyGrouponMsg(ListWxCondition listWxCondition) {
        Map map = new HashMap();
        //创建要封装的链表
        List<MyGrouponAll> myGrouponAlls = new ArrayList<>();
        GrouponExample grouponExample = new GrouponExample();

        List<GrouponWx> grouponWxes = wxMyGrouponMapper.selectMsg();
        //遍历团购表
        for (GrouponWx grouponWx : grouponWxes) {
            //根据orderid查询订单表
            Order order = wxMyGrouponMapper.selectByOrderId(grouponWx.getOrderId());
            MyGrouponWxA myGrouponWxA = new MyGrouponWxA();
            //判断状态码
            if (order.getOrderStatus() == 101) {
                myGrouponWxA.setOrderStatusText("未付款");
            } else if (order.getOrderStatus() == 102) {
                myGrouponWxA.setOrderStatusText("用户取消");
            } else if (order.getOrderStatus() == 103) {
                myGrouponWxA.setOrderStatusText("系统取消");
            } else if (order.getOrderStatus() == 201) {
                myGrouponWxA.setOrderStatusText("已付款");
            } else if (order.getOrderStatus() == 202) {
                myGrouponWxA.setOrderStatusText("申请退款");
            } else if (order.getOrderStatus() == 203) {
                myGrouponWxA.setOrderStatusText("已退款");
            } else if (order.getOrderStatus() == 301) {
                myGrouponWxA.setOrderStatusText("已发货");
            } else if (order.getOrderStatus() == 401) {
                myGrouponWxA.setOrderStatusText("未付款");
            } else if (order.getOrderStatus() == 402) {
                myGrouponWxA.setOrderStatusText("系统收货");
            }
            myGrouponWxA.setGroupon(grouponWx);
            myGrouponWxA.setCreator(order.getOrderSn());//A 结构完成

            //构造B结构
            MyGrouponWxB myGrouponWxB = new MyGrouponWxB();
            myGrouponWxB.setOrderId(order.getId());
            myGrouponWxB.setActualPrice(order.getActualPrice());
            //统计grouponid 一样的总数就是参与该团购的总人数
            int countGrouponId = wxMyGrouponMapper.countGrouponId(grouponWx.getGrouponId());
            myGrouponWxB.setJoinerCount(countGrouponId);
            //根据orderid 查询 商品
            List<MyGrouponGoodList> myGrouponGoodLists = wxMyGrouponMapper.selectGoodList(grouponWx.getOrderId());
            myGrouponWxB.setGoodsList(myGrouponGoodLists);

            //根据rulesId查询规则表信息
            GrouponRulsWX rulsWX = wxMyGrouponMapper.selectRulrsByRuleId(grouponWx.getRulesId());
            myGrouponWxB.setRules(rulsWX);//B结构完成

            //构造C结构
            MyGrouponWxC myGrouponWxC = new MyGrouponWxC();

            //判断登录的用户的id 是否等于 creator id
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if (user.getId() == grouponWx.getCreatorUserId()) {
                myGrouponWxC.setIsCreator(1);
            } else {
                myGrouponWxC.setIsCreator(0);
            }

            myGrouponWxC.setId(user.getId());
            //判断选择的权利
            HandleOptionWx handleOptionWx = new HandleOptionWx();
            //如果已经付款
            if (grouponWx.getPayed() == 1) {
                //则不能取消
                handleOptionWx.setCancel(0);
                //可以删除
                handleOptionWx.setDelete(1);
                //不用付款
                handleOptionWx.setPay(0);
                //可以评论
                handleOptionWx.setComment(1);
                //可以重新购买
                handleOptionWx.setRebuy(1);
                //可以退款
                handleOptionWx.setRefund(1);
            } else {
                handleOptionWx.setCancel(1);
                handleOptionWx.setDelete(0);
                handleOptionWx.setPay(1);
                handleOptionWx.setComment(0);
                handleOptionWx.setRebuy(0);
                handleOptionWx.setRefund(1);
            }
            //判断能否能确认收货
            if (grouponWx.getPayed() == 1 && myGrouponWxA.getOrderStatusText() == "已发货" && myGrouponWxA.getOrderStatusText() == "已付款") {
                handleOptionWx.setConfirm(1);
            } else {
                handleOptionWx.setConfirm(0);
            }
            myGrouponWxC.setHandleOptionWx(handleOptionWx);

            MyGrouponAll myGrouponAll = new MyGrouponAll();
            myGrouponAll.setMyGrouponWxA(myGrouponWxA);
            myGrouponAll.setMyGouponsWxB(myGrouponWxB);
            myGrouponAll.setMyGrouponWxC(myGrouponWxC);
            //塞进表
            myGrouponAlls.add(myGrouponAll);
        }

        map.put("data", myGrouponAlls);
        map.put("count", myGrouponAlls.size());

        return map;
    }

}

