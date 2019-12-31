package com.cskaoyan.service;


import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.Role;

import java.util.Map;

public interface SystemManagerServie {

    Map queryRoles(ListCondition listCondition);

    Role update(Role role);

    Role addRole(Role role);

    Role deleteRole(Role role);
}
