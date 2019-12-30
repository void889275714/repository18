package com.cskaoyan.controller;

import com.cskaoyan.bean.*;
import com.cskaoyan.exception.ItemAlreadyExistException;
import com.cskaoyan.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
     * @param listCondition
     * @return
     */
    @RequestMapping("list")
    public BaseRespVo queryAllGoods(ListCondition listCondition){
        BaseRespVo baseRespVo = new BaseRespVo();
        if(listCondition == null){
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("查询失败");
        } else {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
            Map map = goodsService.queryAllGoods(listCondition);
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

    /**
     * 查询所有的类别以及其子类别信息和品牌信息
     * 请求参数：无
     * 响应体：{
     * 	"errno": 0,
     * 	"data": {
     * 		"categoryList": [{
     * 			"value": 1005000,
     * 			"label": "居家",
     * 			"children": [子类别list]
     *                }],
     * 		"brandList": {[品牌的list]},
     * 	"errmsg": "成功"
     * }
     * @return
     */
    @RequestMapping("catAndBrand")
    public BaseRespVo queryCatAndBrand(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String, Object> map = goodsService.queryCatAndBrand();
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 更新商品相关的所有信息
     * @param goodsDetail
     * @return
     */
    @RequestMapping("update")
    public BaseRespVo updateGoodsDetail(@RequestBody GoodsDetail goodsDetail){
        goodsService.updateGoodsDetail(goodsDetail);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 添加订单
     * @param goodsDetail
     * @return
     */
    @RequestMapping("create")
    public BaseRespVo createGoods(@RequestBody GoodsDetail goodsDetail) throws ItemAlreadyExistException {
        BaseRespVo baseRespVo = new BaseRespVo();
        goodsService.createGoods(goodsDetail);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 删除商品（假删）
     * @param goods
     * @return
     */
    @RequestMapping("delete")
    public BaseRespVo deleteGoods(@RequestBody Goods goods){
        BaseRespVo baseRespVo = new BaseRespVo();
        goodsService.deleteGoods(goods);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
