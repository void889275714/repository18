package com.stylefeng.guns.rest.modular.auth.filter;

import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.util.RenderUtil;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.netty.util.internal.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    private List<String> urlList;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
         urlList = jwtProperties.getUrlList();
        String requestURI = request.getRequestURI();
        if (!requestURI.contains("alipay")&&!urlList.contains(requestURI)&&!requestURI.contains(urlList.get(0))){
            final String requestHeader = request.getHeader(jwtProperties.getHeader());
            String authToken = null;
            if (requestHeader != null && requestHeader.startsWith("Bearer ")){
                authToken = requestHeader.substring(7);
                try {
                    jwtTokenUtil.parseToken(authToken);
                } catch (ExpiredJwtException e) {
                    throw new JwtException("token失效");
                }
                String username = jwtTokenUtil.getUsernameFromToken(authToken);
                String redisToken = (String) redisTemplate.opsForValue().get(username);
                if (StringUtil.isNullOrEmpty(redisToken)) return;
                //更新登录时间
                redisTemplate.opsForValue().set(username,authToken,1800, TimeUnit.SECONDS);
            }else return;
        }
        //放行
        chain.doFilter(request, response);

    }
}
