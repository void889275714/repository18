package com.cskaoyan.service;


import com.cskaoyan.bean.GrouponReceive;
import com.cskaoyan.bean.Groupon_rules;
import com.cskaoyan.bean.ListCondition;


import java.util.Map;

public interface GruoponService {

    Groupon_rules addRules(GrouponReceive grouponReceive);

    boolean queryGoodsId(String goodsId);

    Map queryActivity(ListCondition listCondition);
}
