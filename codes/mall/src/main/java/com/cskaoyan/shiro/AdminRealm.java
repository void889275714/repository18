package com.cskaoyan.shiro;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.AdminExample;
import com.cskaoyan.mapper.AdminMapper;
import com.cskaoyan.mapper.PermissionMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    PermissionMapper permissionMapper;


    /**
     * 认证！
     * 就做了一件事
     * 判断传过来的账号密码对的上不
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        //在数据库进行认证
        AdminExample example = new AdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(example);
        Admin admin = admins.get(0);
        String password = admin.getPassword();
        //核心三个参数 principal  credentials   realmName
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin, password, getName());
        return authenticationInfo;
    }


    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
        //这一步是干什么的
        String username = admin.getUsername();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> permissions = permissionMapper.selectPermissionByRoleId(admin.getRoleIds(), false);
        //进行授权
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }


}
