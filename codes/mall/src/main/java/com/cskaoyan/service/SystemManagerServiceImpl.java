package com.cskaoyan.service;

import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.Role;
import com.cskaoyan.bean.RoleExample;
import com.cskaoyan.mapper.AdminMapper;
import com.cskaoyan.mapper.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bruce
 */
@Service
public class SystemManagerServiceImpl implements SystemManagerServie {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RoleMapper roleMapper;

    /**
     * 显示查询到的角色
     * @param listCondition
     * @return
     */
    @Override
    public Map queryRoles(ListCondition listCondition) {
        // 开启分页
        PageHelper.startPage(listCondition.getPage(),listCondition.getLimit());

        // 调用逆向工程的example
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();

        //获得查询的角色名称
        String name = listCondition.getName();
        // 判断查询条件
        if (name != null && name.length() != 0 && name != "") {
            criteria.andNameLike("%" + name + "%");
        }

        // 拼接查询条件
        roleExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        // 查询 roles
        List<Role> roles = roleMapper.selectByExample(roleExample);
        // 得到总条目数
        PageInfo<Role> rolePageInfo = new PageInfo<>(roles);
        long total = rolePageInfo.getTotal();

        // 把数据放入map
        HashMap hashMap = new HashMap();
        hashMap.put("total",total);
        hashMap.put("items",roles);
        return hashMap;
    }

    /**
     * 更新管理员的名称和说明
     * @param role
     */
    @Override
    public Role update(Role role) {
        role.setUpdateTime(new Date());
        roleMapper.updateByPrimaryKey(role);
        return role;
    }

    /**
     * 插入一条新的角色
     * @param role
     * @return
     */
    @Override
    public Role addRole(Role role) {
        // 把得到的角色设置参数
        role.setEnabled(true);
        role.setAddTime(new Date());
        role.setUpdateTime(new Date());
        role.setDeleted(false);
        roleMapper.insertSelective(role);
        return role;
    }

    /**
     * 删除一个角色
     * @param role
     * @return
     */
    /**
     *  如果是管理员角色，删除失败 {"errno":642,"errmsg":"当前角色存在管理员，不能删除"}
      */
    @Override
    public Role deleteRole(Role role) {
        return null;
    }
}
