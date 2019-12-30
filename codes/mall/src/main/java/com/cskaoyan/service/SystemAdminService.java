package com.cskaoyan.service;

import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.ListAdminCondition;

import java.util.Map;

public interface SystemAdminService {
     Map<String,Object> showAdminList(ListAdminCondition listAdminCondition);

     Admin createAdmin(Admin admin);

     Admin updateAdmin(Admin admin);

     boolean deleteAdmin(Admin admin);
}
