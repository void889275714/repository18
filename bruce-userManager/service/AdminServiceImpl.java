package com.cskaoyan.service;


import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.AdminExample;
import com.cskaoyan.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public boolean queryAdminForLogin(String username,String password){
        AdminExample adminExample = new AdminExample();
        Admin admin = (Admin) adminMapper.selectByExample(adminExample);
        if (admin == null) {
            return false;
        }
        if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
