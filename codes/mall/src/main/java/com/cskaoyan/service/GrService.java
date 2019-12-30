package com.cskaoyan.service.configmall;

import com.cskaoyan.bean.Groupon_rules;
import com.cskaoyan.bean.configmall.CreateGrMsg;
import com.cskaoyan.bean.configmall.ListGrCondition;

import java.util.Map;

public interface GrService {

    Map queryGrMsg(ListGrCondition listGrCondition);

    Groupon_rules insertGrMsg(CreateGrMsg createGrMsg);

    boolean deleteGrMsg(Groupon_rules groupon_rules);

    boolean updateGrmsg(Groupon_rules groupon_rules);
}
