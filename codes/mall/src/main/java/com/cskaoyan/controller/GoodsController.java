package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GoodsDetail;
import com.cskaoyan.bean.ListConditon;
import com.cskaoyan.service.GoodsService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2019-12-26 22:17
 * @Created by ouyangfan
 */
@RestController
@RequestMapping("admin/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;


    /**
     * 查询符合条件的商品信息的集合
     * 请求参数：page、limit、goodsSn、name、sort、order
     * 响应体：
     * {"errno": 0,"data": {"total": 245,"items": [商品表数据的list]}}
     * @param listConditon
     * @return
     */
    @RequestMapping("list")
    public BaseRespVo queryAllGoods(ListConditon listConditon){
        BaseRespVo baseRespVo = new BaseRespVo();
        if(listConditon == null){
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("查询失败");
        } else {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
            List<Goods> goods = goodsService.queryAllGoods(listConditon);
            Long total = goodsService.countGoods();
            Map map = new HashMap();
            map.put("total", total);
            map.put("items", goods);
            baseRespVo.setData(map);
        }
        return baseRespVo;
    }

    /**
     * 查询商品相关的所有信息
     * 请求参数：id
     * 响应体：
     *
     * @return
     */
    @RequestMapping("detail")
    public BaseRespVo queryGoodsDetail(int id){
        BaseRespVo baseRespVo = new BaseRespVo();
        GoodsDetail goodsDetail = goodsService.queryGoodsDetail(id);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(goodsDetail);
        return baseRespVo;
    }
}
