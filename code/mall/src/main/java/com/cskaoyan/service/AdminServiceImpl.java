package com.cskaoyan.service;

import com.cskaoyan.bean.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public boolean queryAdminForLogin(Admin admin) {
        return false;
    }
}
