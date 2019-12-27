package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.LoginBean;
import com.cskaoyan.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bruce
 * 用户管理模块的API
 */
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
       /* String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        boolean flag = adminService.queryAdminForLogin(username,password);*/
        /*if (flag) {
            baseRespVo.setErrno(0);
            //data的值以后还要修改，因为info 请求会用到*/
            baseRespVo.setData("All-Star");
            baseRespVo.setErrmsg("成功");
            return baseRespVo;
       /* } else {
            baseRespVo.setErrno(605);
            baseRespVo.setErrmsg("用户账号或密码不正确");
            return baseRespVo;
        }*/
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

    /**
     * 用户管理模块，显示用户管理页面和插叙用户的方法
     * @author bruce-g
     * @param requestBody
     * @return
     */
    @RequestMapping("admin/user/list")
    public BaseRespVo managerUser(ListCondition requestBody) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map returnMap = adminService.queryUsers(requestBody);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(returnMap);
        return baseRespVo;
    }

    /**
     *用户管理模块中的收货地址
     * @param listCondition
     * @return
     */
    @RequestMapping("admin/address/list")
    public BaseRespVo addressList(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();

            Map map = adminService.queryAddress(listCondition);
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
            baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 会员收藏模块
     * @return
     */
    @RequestMapping("admin/collect/list")
    public BaseRespVo userCollect(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = adminService.queryUserCollectList(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 会员的足迹显示
     */
    @RequestMapping("admin/footprint/list")
    public BaseRespVo UserFootPrint(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = adminService.queryUserFootPrint(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 搜索历史模块的方法，包括展示和搜索功能
     *
     */
    @RequestMapping("admin/history/list")
    public BaseRespVo SearchHistory(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = adminService.queryUserSearchHistory(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 意见反馈的模块
     */
    @RequestMapping("admin/feedback/list")
    public BaseRespVo UserFeedback(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = adminService.queryComment(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }
}
