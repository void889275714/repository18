package com.cskaoyan.controller.wx;

import com.cskaoyan.annotation.LogRecord;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.LoginBean;
import com.cskaoyan.bean.User;
import com.cskaoyan.shiro.MallToken;
import com.cskaoyan.util.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WxUserController {

    /**
     * 微信端登录
     * @param loginBean
     * @return
     * @throws Exception
     */
    @RequestMapping("wx/auth/login")
    public BaseRespVo login(@RequestBody LoginBean loginBean) throws Exception {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
//        if (null != SecurityUtils.getSubject().getPrincipal()) {
//            SecurityUtils.getSubject().logout();
//        }
        Subject subject = SecurityUtils.getSubject();
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        //String md5 = Md5Util.getMd5(password, username);
        MallToken token = new MallToken(username, password, "wx");
        try{
            //在shiro --> WxRealm 进行验证
            subject.login(token);
        } catch (AuthenticationException e) {
            baseRespVo.setErrno(501);
            baseRespVo.setErrmsg("微信端登录失败，请重新登录！");
            return baseRespVo;
        }
        //获得其sessionId
        Serializable id = subject.getSession().getId();
        //获得其实例
        User user = (User) subject.getPrincipal();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map.put("token",id);
        map.put("userInfo",map2);

        map2.put("nickName",user.getNickname());
        map2.put("avatarUrl",user.getAvatar());
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }


    /**
     * 微信端 --> 登出
     * @return
     * @throws Exception
     */
    @RequestMapping("wx/auth/logout")
    public BaseRespVo logout() throws Exception {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
