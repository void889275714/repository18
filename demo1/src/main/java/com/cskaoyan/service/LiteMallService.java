package com.cskaoyan.service;


import com.cskaoyan.bean.FreightMsg;
import com.cskaoyan.bean.MallMsg;
import com.cskaoyan.bean.OrderMsg;
import com.cskaoyan.bean.WxMsg;

import java.util.Map;

public interface LiteMallService {


    Map queryMallMsg();

    boolean updateMallMsg(MallMsg mallMsg);

    Map queryExpressMsg();

    boolean updateFreightMsg(FreightMsg freightMsg);

    Map queryOrderMsg();

    boolean updateOrderMsg(OrderMsg orderMsg);

    Map queryWxMsg();

    boolean updateWxMsg(WxMsg wxMsg);
}
