package com.cskaoyan.service;


import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Autowired
    UserMapper userMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    FootprintMapper footprintMapper;
    @Autowired
    Search_historyMapper search_historyMapper;
    @Autowired
    FeedbackMapper feedbackMapper;

    /**
     * 用户管理的模块，显示用户和查询的方法
     *
     * @param listCondition
     * @return
     */
    @Override
    public Map queryUsers(ListCondition listCondition) {
        int pageNum = listCondition.getPage();
        int size = listCondition.getLimit();
        PageHelper.startPage(pageNum, size);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        String mobile = listCondition.getMobile();
        String username = listCondition.getUsername();
        // 判断查询条件
        if (username != null && username.length() != 0 && username != "") {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (mobile != null && mobile.length() != 0 && mobile != "") {
            criteria.andMobileEqualTo(mobile);
        }
        userExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", users);
        return map;
    }

    /**
     * 收货地址的模块显示和搜索功能
     *
     * @param listCondition
     * @return
     */
    @Override
    public Map queryAddress(ListCondition listCondition) {
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        String name = listCondition.getName();
        if (name != null && name.length() != 0 && name != "") {
            criteria.andNameLike("%" + name + "%");
        }
        String userId = listCondition.getUserId();
        if (userId != null && userId != "" && userId.length() != 0) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        addressExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Address> addressList = addressMapper.selectByExample(addressExample);
        PageInfo<Address> addressPageInfo = new PageInfo<>();
        long total = addressPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", addressList);
        return map;
    }

    /**
     * 会员收藏的显示和搜索功能
     *
     * @param listCondition
     * @return
     */
    @Override
    public Map queryUserCollectList(ListCondition listCondition) {
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        String valueId = listCondition.getValueId();
        String userId = listCondition.getUserId();
        if (userId != null && userId != "" && userId.length() != 0) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (valueId != null && valueId != "" && valueId.length() != 0) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        collectExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Collect> collectList = collectMapper.selectByExample(collectExample);

        PageInfo<Address> addressPageInfo = new PageInfo<>();
        long total = addressPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", collectList);
        return map;
    }

    /**
     * 会员足迹的展示和搜索功能
     *
     * @param listCondition
     * @return
     */
    @Override
    public Map queryUserFootPrint(ListCondition listCondition) {
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());
        FootprintExample footprintExample = new FootprintExample();
        FootprintExample.Criteria criteria = footprintExample.createCriteria();
        String goodsId = listCondition.getGoodsId();
        String userId = listCondition.getUserId();
        if (userId != null && userId != "" && userId.length() != 0) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (goodsId != null && goodsId != "" && goodsId.length() != 0) {
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        footprintExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Footprint> footprints = footprintMapper.selectByExample(footprintExample);
        PageInfo<Footprint> footprintPageInfo = new PageInfo<>();
        long total = footprintPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", footprints);
        return map;
    }

    /**
     * 搜索历史的展示和搜索功能
     *
     * @param listCondition
     * @return
     */
    @Override
    public Map queryUserSearchHistory(ListCondition listCondition) {
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());
        Search_historyExample search_historyExample = new Search_historyExample();
        Search_historyExample.Criteria criteria = search_historyExample.createCriteria();
        String keyword = listCondition.getKeyword();
        String userId = listCondition.getUserId();
        if (userId != null && userId != "" && userId.length() != 0) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (keyword != null && keyword != "" && keyword.length() != 0) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        search_historyExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Search_history> histories = search_historyMapper.selectByExample(search_historyExample);
        PageInfo<Search_history> pageInfo = new PageInfo<>();
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", histories);
        return map;
    }

    /**
     * 意见反馈的展示和搜索功能
     * @param listCondition
     * @return
     */
    @Override
    public Map queryComment(ListCondition listCondition) {
        PageHelper.startPage(listCondition.getPage(),listCondition.getLimit());
        FeedbackExample feedbackExample = new FeedbackExample();
        FeedbackExample.Criteria criteria = feedbackExample.createCriteria();
        String username = listCondition.getUsername();
        String id = listCondition.getId();
        if (username != "" && username != null && username.length() != 0) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (id != "" && id != null && id.length() != 0) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        feedbackExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Feedback> feedbackList = feedbackMapper.selectByExample(feedbackExample);
        PageInfo<Feedback> pageInfo = new PageInfo<>();
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", feedbackList);
        return map;
    }
}
