package com.cskaoyan.controller.wx;

import com.cskaoyan.annotation.LogRecord;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.LoginBean;
import com.cskaoyan.bean.User;
import com.cskaoyan.shiro.MallToken;
import com.cskaoyan.util.Md5Util;
import com.fasterxml.jackson.databind.ser.Serializers;
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



    /*--------------------------------------User/auth-----------------------------------------------*/

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

    /**
     * 假回复
     * 用户登录后会显示其订单信息
     * @return
     */
    @RequestMapping("wx/user/index")
    public String userIndex(){
        //拿到参数后也可以修改String语句，拼接的方法返回json字符串，最初的方法
        String resp = "{\"errno\":0,\"data\":{\"order\":{\"unrecv\":0,\"uncomment\":0,\"unpaid\":1,\"unship\":0}},\"errmsg\":\"成功\"}";
        return resp;
    }


    /**
     * 发送验证码的
     */
    @RequestMapping("wx/auth/regCaptcha")
    public String authReg(){
        String resp = "{\"errno\":701,\"errmsg\":\"小程序后台验证码服务不支持。 Ps : 别请求了，直接用lanzhao,密码123登录吧\"}";
        return resp;
    }


    /**
     * 有空了做一下验证码
     * @return
     */
    @RequestMapping("wx/auth/register")
    public String register(){
        String resp = "{\"errno\":703,\"errmsg\":\"验证码错误\"}";
        return resp;
    }

    /**
     * 微信登录
     * 真的能拿到你的微信信息
     */
    @RequestMapping("wx/auth/login_by_weixin")
    public String login_by_weixin(){
        String resp = "{\"errno\":-1,\"errmsg\":\"错误\"}";
        return resp;
    }

    /**
     * 密码重置
     * 等验证码弄好了试试
     * @return
     */
    @RequestMapping("wx/auth/reset")
    public String reset(){
        String resp = "{\"errno\":703,\"errmsg\":\"验证码错误\"}";
        return resp;
    }
}
