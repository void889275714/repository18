package com.cskaoyan.controller;

import com.cskaoyan.bean.*;
import com.cskaoyan.service.SystemAdminService;
import com.cskaoyan.service.LogService;
import com.cskaoyan.service.RoleService;
import com.cskaoyan.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class AdminSystemController {

    /*-----------------------------系统管理------------------------------------*/


    /*-----------------------------管理员------------------------------------*/

    @Autowired
    SystemAdminService systemAdminService;


    /**
     * 管理员 --> 显示
     */
    @RequestMapping("admin/admin/list")
    public BaseRespVo showAdmin(ListAdminCondition listAdminCondition){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String, Object> map = systemAdminService.showAdminList(listAdminCondition);
        baseRespVo.setData(map);
        return baseRespVo;
    }


    @Autowired
    RoleService roleService;

    /**
     * 用到了role的方法，拿取了 cskaoyan_mall_role的id 和 name
     */
    @RequestMapping("admin/role/options")
    public BaseRespVo cateGoryL1Show(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        List<Map<String,Object>> roleList = roleService.roleList();
        baseRespVo.setData(roleList);
        return baseRespVo;
    }


    /**
     * 管理员 -- > 新增
     * @param admin
     * @return
     * @throws ParseException
     */
    @RequestMapping("admin/admin/create")
    public BaseRespVo adminCreate(@RequestBody Admin admin) throws Exception {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = simpleDateFormat.parse(format);
        admin.setAddTime(parse);
        admin.setUpdateTime(parse);
        admin.setDeleted(false);
        //在此处可以进行对密码加密，也可以移至 service 层进行处理,加盐了
        String md5 = Md5Util.getMd5(admin.getPassword(),admin.getUsername());
        admin.setPassword(md5);

        Admin data= systemAdminService.createAdmin(admin);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;

    }


    /**
     * 管理员 --> 修改
     * 把逻辑层放到service层了
     * @param admin
     * @return
     */
    @RequestMapping("admin/admin/update")
    public BaseRespVo adminUpdate(@RequestBody Admin admin){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Admin data= systemAdminService.updateAdmin(admin);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 管理员 --> 删除
     *
     */
    @RequestMapping("admin/admin/delete")
    public BaseRespVo categoryDelete(@RequestBody Admin admin){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        boolean data= systemAdminService.deleteAdmin(admin);
        if (data) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }
        return baseRespVo;
    }

    /*-----------------------------操作日志------------------------------------*/


    @Autowired
    LogService logService;

    @RequestMapping("admin/log/list")
    public BaseRespVo showLog(ListLogCondition listLogCondition){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String, Object> map = logService.showLogList(listLogCondition);
        baseRespVo.setData(map);
        return baseRespVo;
    }


}
