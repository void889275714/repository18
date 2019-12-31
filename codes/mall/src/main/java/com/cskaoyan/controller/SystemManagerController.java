package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.Role;
import com.cskaoyan.service.SystemManagerServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 删除角色
     */
    @RequestMapping("delete")
    public BaseRespVo deleteRole(@RequestBody Role role) {
        role = systemManagerServie.deleteRole(role);
        return BaseRespVo.ok(role);
    }
}
