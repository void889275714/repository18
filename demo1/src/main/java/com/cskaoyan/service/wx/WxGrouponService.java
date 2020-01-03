package com.cskaoyan.service.wx;

import com.cskaoyan.bean.ListWxCondition;

import java.util.Map;

public interface WxGrouponService {
    Map queryWxGrouponMsg(ListWxCondition listWxCondition);

    Map queryMyGrouponMsg(ListWxCondition listWxCondition);
}
