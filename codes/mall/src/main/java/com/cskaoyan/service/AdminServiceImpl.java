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
        //获取参数中的分页变量和分页条数限制
        int pageNum = listCondition.getPage();
        int size = listCondition.getLimit();
        //开启分页
        PageHelper.startPage(pageNum, size);
        // 调用逆向工程的example
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        // 获得参数中的两个变量，在页面上需要这两个参数进行查询
        String mobile = listCondition.getMobile();
        String username = listCondition.getUsername();

        // 判断查询条件 因为查询一次之后再删除继续查询时，请求体中还时携带这个参数过来，需要判断它不为“”再查
        if (username != null && username.length() != 0 && username != "") {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (mobile != null && mobile.length() != 0 && mobile != "") {
            criteria.andMobileEqualTo(mobile);
        }
        // 取得两个参数拼接，是查询的条件，例如根据添加时间降序排序
        userExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<User> users = userMapper.selectByExample(userExample);
        // 需要计算查询的总数
        PageInfo<User> pageInfo = new PageInfo<>(users);
        long total = pageInfo.getTotal();
        //放入 map中
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
        //获取参数中的分页变量和分页条数限制，开启分页
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());

        // 调用逆向工程的example
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();

        // 获得参数中的两个变量，在页面上需要这两个参数进行查询
        String name = listCondition.getName();
        String userId = listCondition.getUserId();

        // 判断查询条件
        if (name != null && name.length() != 0 && name != "") {
            criteria.andNameLike("%" + name + "%");
        }
        if (userId != null && userId != "" && userId.length() != 0) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }

        // 取得两个参数拼接，是查询的条件，例如根据添加时间降序排序
        addressExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Address> addressList = addressMapper.selectByExample(addressExample);

        // 需要计算查询的总数
        PageInfo<Address> addressPageInfo = new PageInfo<>(addressList);
        long total = addressPageInfo.getTotal();

        // 放入map中
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
        //获取参数中的分页变量和分页条数限制，开启分页
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());

        // 调用逆向工程的example
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();

        // 获得参数中的两个变量，在页面上需要这两个参数进行查询
        String valueId = listCondition.getValueId();
        String userId = listCondition.getUserId();

        // 判断查询条件
        if (userId != null && userId != "" && userId.length() != 0) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (valueId != null && valueId != "" && valueId.length() != 0) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }

        // 取得两个参数拼接，是查询的条件，例如根据添加时间降序排序
        collectExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Collect> collectList = collectMapper.selectByExample(collectExample);

<<<<<<< HEAD
        PageInfo<Address> addressPageInfo = new PageInfo<>(collectList);
=======
        // 需要计算查询的总数
        PageInfo<Collect> addressPageInfo = new PageInfo<>(collectList);
>>>>>>> cb9087a8db5bfea50f10aef15ba95b94980b2266
        long total = addressPageInfo.getTotal();

        // 放入map中
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
        //获取参数中的分页变量和分页条数限制，开启分页
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());

        // 调用逆向工程的example
        FootprintExample footprintExample = new FootprintExample();
        FootprintExample.Criteria criteria = footprintExample.createCriteria();

        // 获得参数中的两个变量，在页面上需要这两个参数进行查询
        String goodsId = listCondition.getGoodsId();
        String userId = listCondition.getUserId();

        // 判断查询条件
        if (userId != null && userId != "" && userId.length() != 0) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (goodsId != null && goodsId != "" && goodsId.length() != 0) {
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }

        // 取得两个参数拼接，是查询的条件，例如根据添加时间降序排序
        footprintExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Footprint> footprints = footprintMapper.selectByExample(footprintExample);

        // 需要计算查询的总数
        PageInfo<Footprint> footprintPageInfo = new PageInfo<>(footprints);
        long total = footprintPageInfo.getTotal();

        //放入map中
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
        //获取参数中的分页变量和分页条数限制，开启分页
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());

        // 调用逆向工程的example
        Search_historyExample search_historyExample = new Search_historyExample();
        Search_historyExample.Criteria criteria = search_historyExample.createCriteria();

        // 获得参数中的两个变量，在页面上需要这两个参数进行查询
        String keyword = listCondition.getKeyword();
        String userId = listCondition.getUserId();

        // 判断查询条件 因为查询一次之后再删除继续查询时，请求体中还时携带这个参数过来，需要判断它不为“”再查
        if (userId != null && userId != "" && userId.length() != 0) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (keyword != null && keyword != "" && keyword.length() != 0) {
            criteria.andKeywordLike("%" + keyword + "%");
        }

        // 取得两个参数拼接，是查询的条件，例如根据添加时间降序排序
        search_historyExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Search_history> histories = search_historyMapper.selectByExample(search_historyExample);

        // 需要计算查询的总数
        PageInfo<Search_history> pageInfo = new PageInfo<>(histories);
        long total = pageInfo.getTotal();

        //放入map中
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
        //获取参数中的分页变量和分页条数限制，开启分页
        PageHelper.startPage(listCondition.getPage(),listCondition.getLimit());

        // 调用逆向工程的example
        FeedbackExample feedbackExample = new FeedbackExample();
        FeedbackExample.Criteria criteria = feedbackExample.createCriteria();

        // 获得参数中的两个变量，在页面上需要这两个参数进行查询
        String username = listCondition.getUsername();
        String id = listCondition.getId();

        // 判断查询条件 因为查询一次之后再删除继续查询时，请求体中还时携带这个参数过来，需要判断它不为“”再查
        if (username != "" && username != null && username.length() != 0) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (id != "" && id != null && id.length() != 0) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }

        // 取得两个参数拼接，是查询的条件，例如根据添加时间降序排序
        feedbackExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        List<Feedback> feedbackList = feedbackMapper.selectByExample(feedbackExample);

        // 需要计算查询的总数
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbackList);
        long total = pageInfo.getTotal();

        //放入map中
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", feedbackList);
        return map;
    }
}
