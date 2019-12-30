package com.cskaoyan.service;

import com.cskaoyan.bean.ListCondition;

import java.util.Map;

public interface MallOrderService {

    Map selectOrder(ListCondition listCondition);

    Map queryOrderPage(int id);
}
