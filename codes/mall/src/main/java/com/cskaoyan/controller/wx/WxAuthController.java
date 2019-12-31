package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.LoginBean;
import com.cskaoyan.service.SystemAdminService;
import com.cskaoyan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("wx/auth")
public class WxAuthController {

    @Autowired
    SystemAdminService adminService;

    @Autowired
    UserService userService;

    @RequestMapping("login")
    public BaseRespVo login(@RequestBody LoginBean loginBean) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
       //先用请求头的session,后面shiro会用了再修改
        HttpSession session = request.getSession();
        session.setAttribute("username",loginBean.getUsername());
        session.setAttribute("password",loginBean.getPassword());
        //去数据库判断 先写true
        if (true) {
            baseRespVo.setErrno(0);
            //data的值以后还要修改，因为info 请求会用到
            baseRespVo.setData("All-Star");
            baseRespVo.setErrmsg("成功");
        }
        return baseRespVo;
    }

}
