package com.cskaoyan.controller.wx;


import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.service.wx.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxHomeController {

//    @Autowired
//    HomeService homeService;

    @RequestMapping("wx/home/index")
    public BaseRespVo index(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");

        return baseRespVo;
    }
}
