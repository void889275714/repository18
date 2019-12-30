package com.cskaoyan.service;

import com.cskaoyan.bean.AdminExample;
import com.cskaoyan.bean.ListLogCondition;
import com.cskaoyan.bean.Log;
import com.cskaoyan.bean.LogExample;
import com.cskaoyan.mapper.LogMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService{


    @Autowired
    LogMapper logMapper;


    /**
     * 操作日志的显示与查询
     * @param listLogCondition
     * @return
     */
    @Override
    public Map<String, Object> showLogList(ListLogCondition listLogCondition) {
        //处理参数
        int page = listLogCondition.getPage();
        int limit = listLogCondition.getLimit();
        PageHelper.startPage(page,limit);
        String order = listLogCondition.getOrder();
        String sort = listLogCondition.getSort();
        String adminname = listLogCondition.getName();
        String s = sort + " " + order;

        LogExample logExample = new LogExample();
        logExample.setOrderByClause(s);
        LogExample.Criteria criteria = logExample.createCriteria();
        criteria.andDeletedEqualTo(false);



        if (adminname != null ){
            criteria.andAdminLike("%" + adminname + "%");
        }
        List<Log> logs = logMapper.selectByExample(logExample);
        HashMap<String, Object> map = new HashMap<>();
        long size = logMapper.countByExample(logExample);
        map.put("total",(int)size);
        map.put("items",logs);
        return map;
    }
}
