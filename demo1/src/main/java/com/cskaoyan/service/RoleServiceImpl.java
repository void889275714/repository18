package com.cskaoyan.service;

import com.cskaoyan.bean.CateGory;
import com.cskaoyan.bean.CateGoryExample;
import com.cskaoyan.bean.Role;
import com.cskaoyan.bean.RoleExample;
import com.cskaoyan.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService{


    @Autowired
    RoleMapper roleMapper;
    /**
     * 系统管理 --> 管理员 --> 需要交一个option 的map list
     * @return
     */
    @Override
    public List<Map<String, Object>> roleList() {
        RoleExample roleExample = new RoleExample();
        List<Role> roles = roleMapper.selectByExample(roleExample);

        ArrayList<Map<String,Object>> data = new ArrayList<>();
        for (Role role : roles) {
            HashMap<String, Object> map = new HashMap<>();
            Integer id = role.getId();
            String name = role.getName();
            map.put("value",id);
            map.put("label",name);
            data.add(map);
        }
        return data;
    }
}
