package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.service.WxGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

/**
 * @author bruce
 */
@RestController
@RequestMapping("wx/goods")
public class WxGoodsController {

    @Autowired
    WxGoodsService wxGoodsService;

    /**
     * 获得商品分类数据
     */
    @RequestMapping("category")
    public BaseRespVo showCategory (String id) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxGoodsService.queryCategory(Integer.parseInt(id));
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 获得商品在分类中的list
     * @param listCondition
     * @return
     */
    @RequestMapping("list")
    public BaseRespVo goodsList(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxGoodsService.queryGoodsList(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 计算商品总数
     */
    @RequestMapping("count")
    public BaseRespVo countGoods() {
        BaseRespVo baseRespVo = new BaseRespVo();
        int count = wxGoodsService.countGoods();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(count);
        return baseRespVo;
    }

    /**
     * 商品的detail页面
     */
    @RequestMapping("detail")
    public BaseRespVo goodsDetail(int id) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxGoodsService.goodsDetail(id);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 商品页面中的相关商品
     */
    @RequestMapping("related")
    public BaseRespVo goodsRelated(int id) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxGoodsService.queryRelated(id);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }
}
