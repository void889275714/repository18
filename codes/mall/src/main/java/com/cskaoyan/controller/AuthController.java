//package com.cskaoyan.controller;
//
//
//import com.cskaoyan.shiro.MallToken;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class AuthController {
//
//    @RequestMapping("admin/login")
//    public String adminLogin(String username,String password) {
//        MallToken adminToken = new MallToken(username,password,"admin");
//        Subject subject = SecurityUtils.getSubject();
//        subject.login(adminToken);
//        return "success";
//    }
//
//    /**
//     * 微信端的认证
//     * @param username
//     * @param password
//     * @return
//     */
//    @RequestMapping("wx/login")
//    public String wxLogin(String username,String password) {
//        MallToken adminToken = new MallToken(username,password,"wx");
//        Subject subject = SecurityUtils.getSubject();
//        subject.login(adminToken);
//        return "success";
//    }
//}
