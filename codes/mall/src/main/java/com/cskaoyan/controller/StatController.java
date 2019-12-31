package com.cskaoyan.controller;


import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.service.StatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin/stat")
public class StatController {
    @Autowired
    StatUserService statUserService;

    @GetMapping("/user")
    /**
     * 显示用户统计
     */
    public BaseRespVo queryStatUserMsg(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map map = statUserService.queryStatUserMsg();
        System.out.println("这里是"+map.get("rows"));
        if(map==null){
            baseRespVo.setErrmsg("系统有误");
            baseRespVo.setData(map);
            baseRespVo.setErrno(10000);
            return baseRespVo;
        }
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

    @GetMapping("/goods")
    public  BaseRespVo queryStatGoodsMsg(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map map = statUserService.queryStatGoodsMsg();
        if(map==null){
            baseRespVo.setErrmsg("系统有误");
            baseRespVo.setData(map);
            baseRespVo.setErrno(10000);
            return baseRespVo;
        }
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        return baseRespVo;
    }

}
