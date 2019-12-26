package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.LoginBean;
import com.cskaoyan.bean.User;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class AdminController {

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


    //分页测试
    @Autowired
    UserService userService;

    @RequestMapping("user/list")
    public List<User> userList(int pageNum,int pageSize){
        List<User> users = userService.queryUsers(pageNum, pageSize);
        return users;
    }


 @Autowired
    RegionService regionService;
    /**
     * 商场管理-->行政区域
     *
     */
    @RequestMapping("admin/region/list")
    public BaseRespVo list(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        List<Region> list = regionService.list();
        baseRespVo.setData(list);
        return baseRespVo;
    }




    @Autowired
    KeyWordService keyWordService;
    /**
     * 商场管理--> 关键词
     */
    @RequestMapping("admin/keyword/list")
    public BaseRespVo keyWordList(int page, int limit,String keyword,String url,String sort,String order) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String, Object> map = keyWordService.queryKeyWord(page,limit,keyword,url);
        baseRespVo.setData(map);
        return baseRespVo;
    }

}
