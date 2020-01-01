package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.AdminMapper;
import com.cskaoyan.mapper.PermissionMapper;
import com.cskaoyan.mapper.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author bruce
 */
@Service
public class SystemManagerServiceImpl implements SystemManagerServie {

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    AdminMapper adminMapper;

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
        int i = roleMapper.updateByPrimaryKey(role);
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
     * 查询角色所有权限
     * @param roleId
     * @return
     */
    @Override
    public Map queryPermissionsByRoleId(String roleId) {
        //获取此id的所有孙子层权限
        Integer[] ids = new Integer[1];
        ids[0] = Integer.parseInt(roleId);
        List<String> assignedPermissions = permissionMapper.selectPermissionByRoleId(ids, false);
        //得到所有权限
        List<AllPermissions> allPermissions = permissionMapper.selectFatherAndSonAndGrandsonPermission();
        //得到pid=0的权限数量
        int count = permissionMapper.countTopPermission();
        //开始想错了，以为返回的是此roleId的三层套娃模型，结果是所有的。。。所以为了复用代码，我丧心病狂了----by 欧阳帆
        List<AllPermissions> systemPermissions = allPermissions.subList(0, count);
        Map map = new HashMap();
        map.put("assignedPermissions", assignedPermissions);
        map.put("systemPermissions", systemPermissions);
        return map;
    }

    /**
     * 根据传过来的roleId和权限的list对角色授权
     * @param authorizeBean
     */
    @Override
    public void authorizeByRoleId(AuthorizeBean authorizeBean) {
        String roleId = authorizeBean.getRoleId();
        List<String> permissions = authorizeBean.getPermissions();
        for (String permission : permissions) {
            //插入数据
            Permission permissionToInsert = new Permission();
            permissionToInsert.setRoleId(Integer.parseInt(roleId));
            permissionToInsert.setPermission(permission);
            permissionToInsert.setAddTime(new Date());
            permissionToInsert.setUpdateTime(new Date());
            permissionToInsert.setDeleted(false);
            permissionMapper.insertSelective(permissionToInsert);
        }
    }

    /**
     * 删除一个角色
     * 如果是管理员角色，删除失败 {"errno":642,"errmsg":"当前角色存在管理员，不能删除"}
     * @param role
     * @return
     */
    @Override
    public boolean deleteRole(Role role) {
        //如果有Admin表中的数据正在使用此role，不能删除
        Integer id = role.getId();
        //先查询所有admin表中数据
        List<Admin> admins = adminMapper.selectByExample(new AdminExample());
        for (Admin admin : admins) {
            Integer[] roleIds = admin.getRoleIds();
            //判断是否有admin在使用此角色
            if(Arrays.asList(roleIds).contains(id)){
                //正在使用此角色
                return false;
            }
        }
        //没有admin表中数据正在使用此role,假删
        role.setUpdateTime(new Date());
        role.setDeleted(true);
        roleMapper.updateByPrimaryKeySelective(role);
        return true;
    }
}
