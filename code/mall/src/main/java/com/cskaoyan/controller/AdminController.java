package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.LoginBean;
import com.cskaoyan.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * {
     * 	"errno": 0,
     * 	"data": "7bcf1450-fdef-4c9a-882b-d61d4b11d073",
     * 	"errmsg": "成功"
     * }
     */
    @RequestMapping("admin/auth/login")
    public BaseRespVo login(@RequestBody LoginBean loginBean) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        //data的值以后还要修改，因为info 请求会用到
        baseRespVo.setData("All-Star");
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }


    @RequestMapping("admin/auth/info")
    public BaseRespVo info() {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","admin123");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        ArrayList<String> roleList = new ArrayList<>();
        roleList.add("超级管理员");
        ArrayList<String> permList = new ArrayList<>();
        permList.add("*");
        map.put("roles",roleList);
        map.put("perms",permList);
        baseRespVo.setData(map);
        return baseRespVo;
    }

}
