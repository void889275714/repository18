package com.cskaoyan.service;


import com.cskaoyan.bean.ListCondition;

import java.util.Map;

public interface AdminService {

    boolean queryAdminForLogin(String username,String password);

    Map queryUsers(ListCondition listCondition);

    Map queryAddress(ListCondition listCondition);

    // boolean judgeParam(ListCondition listCondition);

    Map queryUserCollectList(ListCondition listCondition);

    Map queryUserFootPrint(ListCondition listCondition);

    Map queryUserSearchHistory(ListCondition listCondition);

    Map queryComment(ListCondition listCondition);
}
