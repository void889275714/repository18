package com.cskaoyan.service.wx;

import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.OrderSubmit;

import java.util.Map;

/**
 * @Description TODO
 * @Date 2020-01-01 18:41
 * @Created by ouyangfan
 */
public interface WxOrderService {
    Map queryOrderList(ListCondition listCondition);

    Map queryOrderDetatilByOrderId(Integer orderId);

    void changeOrderStatus(Integer orderId, Short changeOrderStatusTo);

    void orderDelete(Integer orderId);

    void orderPrepay(Integer orderId);

    void orderConfirm(Integer orderId);

    Map orderSubmit(OrderSubmit orderSubmit);

}
