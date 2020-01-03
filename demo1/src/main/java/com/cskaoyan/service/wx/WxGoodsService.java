package com.cskaoyan.service.wx;

import com.cskaoyan.bean.ListCondition;


import java.util.Map;

public interface WxGoodsService {
    Map queryCategory(int id);

    Map queryGoodsList(ListCondition listCondition);

    int countGoods();

    Map goodsDetail(int id);

    Map queryRelated(int id);
}
