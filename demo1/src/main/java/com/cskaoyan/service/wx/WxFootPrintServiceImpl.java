package com.cskaoyan.service.wx;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.WxFootPrint;
import com.cskaoyan.mapper.FootprintMapper;
import com.cskaoyan.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bruce
 */
@Service
public class WxFootPrintServiceImpl implements WxFootPrintService {

    @Autowired
    FootprintMapper footprintMapper;
    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 展示用户足迹
     * @return
     */
    @Override
    public Map showList(ListCondition listCondition) {
        //分页限制
        PageHelper.startPage(listCondition.getPage(),listCondition.getLimit());
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 得到userid
        Integer userId = user.getId();
        //拼接查询条件

        //因为新建了一个bean，所以要把两张表得到的东西都放入一个list中
        ArrayList<WxFootPrint> wxFootPrints = new ArrayList<>();
        //获得逆向工程的bean
        FootprintExample footprintExample = new FootprintExample();
        footprintExample.createCriteria().andUserIdEqualTo(userId);
        //查询足迹
        List<Footprint> footprints = footprintMapper.selectByExample(footprintExample);
        for (Footprint footprint : footprints) {
            WxFootPrint wxFootPrint = new WxFootPrint();
            wxFootPrint.setId(footprint.getId());
            Integer goodsId = footprint.getGoodsId();
            wxFootPrint.setGoodsId(goodsId);
            wxFootPrint.setAddTime(footprint.getAddTime());
            //再查goods表
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            wxFootPrint.setName(goods.getName());
            wxFootPrint.setPicUrl(goods.getPicUrl());
            wxFootPrint.setBrief(goods.getBrief());
            wxFootPrint.setRetailPrice(goods.getRetailPrice());
            wxFootPrints.add(wxFootPrint);
        }
        PageInfo<Footprint> footprintPageInfo = new PageInfo<>(footprints);
        //显示查询到的总数
        long total = footprintPageInfo.getTotal();
        //放入map
        HashMap<String, Object> map = new HashMap<>();
        map.put("footprintList",wxFootPrints);
        map.put("totalPages",total);
        return map;
    }
}
