package com.cskaoyan.controller;

import com.cskaoyan.bean.AuthorizeBean;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.Role;
import com.cskaoyan.service.SystemManagerServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("admin/role")
public class SystemManagerController {

    @Autowired
    SystemManagerServie systemManagerServie;

    /**
     * 角色管理的显示和搜索功能
     * @return
     */
    @RequestMapping("list")
    public BaseRespVo roleList(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = systemManagerServie.queryRoles(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 角色管理的编辑功能
     */
        @RequestMapping("update")
        public BaseRespVo updateRoles(@RequestBody Role role) {
            systemManagerServie.update(role);
            return BaseRespVo.ok(role);
    }

    /**
     *角色管理的添加功能
     */
    @RequestMapping("create")
    public BaseRespVo addRole(@RequestBody Role role) {
        role = systemManagerServie.addRole(role);
        return BaseRespVo.ok(role);
    }

    /**
     * 根据角色id查询这个角色的权限
     * @param roleId
     * @return
     */
    @GetMapping("permissions")
    public BaseRespVo queryPermissionsByRoleId(String roleId){
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = systemManagerServie.queryPermissionsByRoleId(roleId);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 对角色进行授权
     * @param authorizeBean
     * @return
     */
    @PostMapping("permissions")
    public BaseRespVo authorizeByRoleId(@RequestBody AuthorizeBean authorizeBean){
        BaseRespVo baseRespVo = new BaseRespVo();
        systemManagerServie.authorizeByRoleId(authorizeBean);
        return baseRespVo;
    }

    /**
     * 删除角色
     * @param role
     * @return
     */
    @RequestMapping("delete")
    public BaseRespVo deleteRole(@RequestBody Role role){
        BaseRespVo baseRespVo = new BaseRespVo();
        boolean b = systemManagerServie.deleteRole(role);
        if(b) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        } else {
            baseRespVo.setErrno(642);
            baseRespVo.setErrmsg("当前角色存在管理员，不能删除");
        }
        return baseRespVo;
    }
}
