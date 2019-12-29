package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.service.MallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author bruce-g
 * 商场管理模块的订单管理
 */
@RestController
public class MallOrderController {

    @Autowired
    MallOrderService mallOrderService;
/**
 * 订单管理的显示和搜索API
 */
    @RequestMapping("admin/order/list")
    public BaseRespVo orderList(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = mallOrderService.selectOrder(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 订单管理中的详情页面的接口
     */
    @RequestMapping("admin/order/detail")
    public BaseRespVo orderPage(int id) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = mallOrderService.queryOrderPage(id);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 订单的退款功能
     */
   // @RequestMapping("admin/order/refund")

}
