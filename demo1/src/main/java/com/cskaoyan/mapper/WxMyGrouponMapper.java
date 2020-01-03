package com.cskaoyan.mapper;

import com.cskaoyan.bean.GrouponRulsWX;
import com.cskaoyan.bean.GrouponWx;
import com.cskaoyan.bean.MyGrouponGoodList;
import com.cskaoyan.bean.Order;

import java.util.List;

public interface WxMyGrouponMapper {

     List<GrouponWx> selectMsg();


     Order selectByOrderId(Integer orderId);

     int countGrouponId(Integer grouponId);

     List<MyGrouponGoodList> selectGoodList(Integer orderId);

     GrouponRulsWX selectRulrsByRuleId(Integer rulesId);
}

