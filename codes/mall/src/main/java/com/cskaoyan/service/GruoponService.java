package com.cskaoyan.service;


import com.cskaoyan.bean.GrouponReceive;
import com.cskaoyan.bean.Groupon_rules;
import com.cskaoyan.bean.ListCondition;


import java.util.Map;

public interface GruoponService {


    Map queryActivity(ListCondition listCondition);
}
