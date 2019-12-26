package com.cskaoyan.service;

import com.cskaoyan.bean.Paging;
import com.cskaoyan.bean.User;
import com.cskaoyan.bean.UserExample;
import com.cskaoyan.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Map queryUsers(Paging paging) {
        int pageNum = paging.getPage();
        int size = paging.getLimit();
        String mobile = paging.getMobile();
        String username = paging.getUsername();
        PageHelper.startPage(pageNum, size);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        List<User> users = null;
        if (username == null && mobile == null) {
           users = userMapper.selectByExample(userExample);
        }
        if (username != null) {
            criteria.andUsernameLike(username);
            users = userMapper.selectByExample(userExample);
        }
        if (mobile != null) {
            criteria.andMobileLike(mobile);
            users = userMapper.selectByExample(userExample);
        }
        if (mobile != null && username != null) {
            criteria.andUsernameLike(username);
            criteria.andMobileLike(mobile);
            users = userMapper.selectByExample(userExample);
        }
        PageInfo<User> pageInfo = new PageInfo<>(users);
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",users);
        return map;
    }
}
