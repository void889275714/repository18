package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.OrderSubmit;
import com.cskaoyan.service.wx.WxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description 微信端订单模块
 * @Date 2020-01-01 17:34
 * @Created by ouyangfan
 */
@RestController
@RequestMapping("wx/order")
public class WxOrderController {
    @Autowired
    WxOrderService wxOrderService;

    /**
     * 根据请求参数中的type查询不同状态的订单，并分页
     * @return
     */
    @RequestMapping("list")
    public BaseRespVo list(ListCondition listCondition){
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxOrderService.queryOrderList(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 获取订单详细信息
     * @param orderId
     * @return
     */
    @RequestMapping("detail")
    public BaseRespVo orderDetail(Integer orderId){
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxOrderService.queryOrderDetatilByOrderId(orderId);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @RequestMapping("cancel")
    public BaseRespVo orderCancel(@RequestBody Integer orderId){
        BaseRespVo baseRespVo = new BaseRespVo();
        //取消订单即将order表中数据的orderStatus修改为102
        short changeOrderStatusTo = 102;
        wxOrderService.changeOrderStatus(orderId, changeOrderStatusTo);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 申请退款
     * @param orderId
     * @return
     */
    @RequestMapping("refund")
    public BaseRespVo orderRefund(@RequestBody Integer orderId){
        BaseRespVo baseRespVo = new BaseRespVo();
        wxOrderService.changeOrderStatus(orderId, (short) 202);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @RequestMapping("delete")
    public BaseRespVo orderDelete(@RequestBody Integer orderId){
        BaseRespVo baseRespVo = new BaseRespVo();
        wxOrderService.orderDelete(orderId);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 确认收货
     * @param orderId
     * @return
     */
    @RequestMapping("confirm")
    public BaseRespVo orderConfirm(@RequestBody Integer orderId){
        BaseRespVo baseRespVo = new BaseRespVo();
        wxOrderService.orderConfirm(orderId);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
    /**
     * 预付款
     * @param orderId
     * @return
     */
    @RequestMapping("prepay")
    public BaseRespVo orderPrepay(@RequestBody Integer orderId){
        BaseRespVo baseRespVo = new BaseRespVo();
        //wxOrderService.orderPrepay(orderId);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("submit")
    public BaseRespVo orderSubmit(@RequestBody OrderSubmit orderSubmit){
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxOrderService.orderSubmit(orderSubmit);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }
}
