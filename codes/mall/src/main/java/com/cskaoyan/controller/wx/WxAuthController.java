package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.LoginBean;
import com.cskaoyan.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wx/auth")
public class WxAuthController {

    @Autowired
    SystemAdminService adminService;

    @RequestMapping("login")
    public BaseRespVo login(@RequestBody LoginBean loginBean) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        //data的值以后还要修改，因为info 请求会用到
        baseRespVo.setData("All-Star");
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

}
