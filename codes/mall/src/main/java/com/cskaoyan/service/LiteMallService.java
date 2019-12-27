package com.cskaoyan.service.configmall;


import com.cskaoyan.bean.configmall.FreightMsg;
import com.cskaoyan.bean.configmall.MallMsg;
import com.cskaoyan.bean.configmall.OrderMsg;
import com.cskaoyan.bean.configmall.WxMsg;

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
