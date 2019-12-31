package com.cskaoyan.shiro;

import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;


public class CustomWebSessionManager extends DefaultWebSessionManager {


    @Override
    protected Serializable getSessionId(ServletRequest request1, ServletResponse response) {
        HttpServletRequest request = (HttpServletRequest) request1;
        //看看请求头是什么，跟这个一致即可
        String header = request.getHeader("X-cskaoyan-mall-Admin-Token");
        if (header != null && !"".equals(header)) {
            return header;
        }
        String header1 = request.getHeader("X-cskaoyan-mall-wx-token");
        if (header1 != null && !"".equals(header1)) {
            return header1;
        }
        return super.getSessionId(request, response);
    }
}
