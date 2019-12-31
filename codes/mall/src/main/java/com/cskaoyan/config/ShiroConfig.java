package com.cskaoyan.config;


import com.cskaoyan.shiro.AdminRealm;
import com.cskaoyan.shiro.CustomRealmAuthenticator;
import com.cskaoyan.shiro.CustomSessionManager;
import com.cskaoyan.shiro.WxRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {


    //login --> anon 匿名
    //index --> 认证之后才能访问
    //info
    //success

    /**
     * ShiroFilterFactoryBean
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //认证失败后重定向的网页，可更改
        shiroFilterFactoryBean.setLoginUrl("/unauthc");
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/admin/auth/login","anon");
        //微信登录界面
        filterChainDefinitionMap.put("/wx/auth/login","anon");
        //微信分类界面
        filterChainDefinitionMap.put("/wx/catalog/**","anon");
        //微信分类 -- > goods 界面
        filterChainDefinitionMap.put("/wx/goods/**","anon");

        filterChainDefinitionMap.put("/wx/search/**","anon");
        filterChainDefinitionMap.put("/wx/brand/detail","anon");
        //authc 代表除了上面的，都需要验证才能操作
        filterChainDefinitionMap.put("/index","anon");
        filterChainDefinitionMap.put("/unauthc","anon");
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


    /**
     * SecurityManager
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(AdminRealm adminRealm, WxRealm wxRealm,
                                                     CustomSessionManager sessionManager,
                                                     CustomRealmAuthenticator authenticator) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(customRealm);
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        //单个realm
        securityManager.setRealms(realms);
        securityManager.setSessionManager(sessionManager);
        securityManager.setAuthenticator(authenticator);
        return securityManager;
    }


    /**
     * AuthorizationAttributeSourceAdvisor
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    @Bean
    public CustomRealmAuthenticator authenticator(AdminRealm adminRealm, WxRealm wxRealm) {
        CustomRealmAuthenticator customRealmAuthenticator = new CustomRealmAuthenticator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        customRealmAuthenticator.setRealms(realms);
        //认证器
        return customRealmAuthenticator;
    }

    /**
     * 登陆后多长时间内有效
     * @return
     */
    @Bean
    public CustomSessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setDeleteInvalidSessions(true);
        customSessionManager.setGlobalSessionTimeout(800000);
        return customSessionManager;
    }


}
