package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.WxAddCart;
import com.cskaoyan.bean.WxProductId;
import com.cskaoyan.service.wx.WxCartService;
import com.cskaoyan.service.wx.WxCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author bruce
 */
@RestController
@RequestMapping("wx/cart")
public class WxCartController {

    @Autowired
    WxCartService wxCartService;

    /**
     * 购物车的index页面
     */
    @RequestMapping("index")
    public BaseRespVo cartIndex(){
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxCartService.showIndex();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 添加购物车
     */
    @RequestMapping("add")
    public BaseRespVo addCart(@RequestBody WxAddCart wxAddCart){
        /**
         * 如果用户没有登录返回501，请登录
         */
        int i = wxCartService.queryAddCart(wxAddCart);
        if (i == -1) {
            BaseRespVo baseRespVo = new BaseRespVo();
            baseRespVo.setErrmsg("请登录");
            baseRespVo.setErrno(501);
            return baseRespVo;
        }
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(i);
        return baseRespVo;
    }

    /**
     * 获取购物车商品件数
     */
    @RequestMapping("goodscount")
    public  BaseRespVo goodsCount() {
        BaseRespVo baseRespVo = new BaseRespVo();
        int i = wxCartService.queryGoodsCount();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(i);
        return baseRespVo;
    }

    /**
     * 更新购物车商品
     */
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Cart cart) {
        BaseRespVo baseRespVo = new BaseRespVo();
        wxCartService.update(cart);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 删除商品
     */
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody WxProductId wxProductId) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxCartService.delete(wxProductId);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 改变购物车的参数
     * @param wxProductId
     * @return
     */
    @RequestMapping("checked")
    public BaseRespVo checked(@RequestBody WxProductId wxProductId) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxCartService.checkedStatus(wxProductId);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 立即下单的api
     */
    @RequestMapping("fastadd")
    public BaseRespVo fastAdd(@RequestBody WxAddCart wxFastAdd) {
        BaseRespVo baseRespVo = new BaseRespVo();
        int i = wxCartService.fastOrder(wxFastAdd);
        if (i == -1) {
            baseRespVo.setErrno(502);
            baseRespVo.setErrmsg("请登录");
        }
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(i);
        return baseRespVo;
    }

    @RequestMapping("checkout")
    public BaseRespVo cartCheckout(CartCheckOutBean checkOutBean){
        BaseRespVo baseRespVo = new BaseRespVo();
        CartCheckOutForReturn cartCheckOutForReturn = wxCartService.cartCheckOut(checkOutBean);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(cartCheckOutForReturn);
        return baseRespVo;
    }
}
