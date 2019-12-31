package com.cskaoyan.service;

import com.cskaoyan.bean.User;
import com.cskaoyan.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> queryUsers(int pageNumber, int size) {
        PageHelper.startPage(pageNumber, size);
        List<User> users = userMapper.selectUsers();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        long total = pageInfo.getTotal();
        System.out.println("总数据量:" + total);
        System.out.println("users size = " + users.size());
        return users;
    }


    /**
     * 查询数据库里是否有这样的值
     * @return
     */
    @Override
    public boolean queryUserBySessionUsername() {

        return false;
    }
}
