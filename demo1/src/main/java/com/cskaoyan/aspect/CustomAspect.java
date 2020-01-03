package com.cskaoyan.aspect;


import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Log;
import com.cskaoyan.mapper.LogMapper;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
public class CustomAspect {

    @Autowired
    LogMapper logMapper;

    /**
     * 第二步: PointCut
     */
    @Pointcut("@annotation(com.cskaoyan.annotation.LogRecord)")
    public void myPointCut() {

    }


    /**
     * 第三步: 在around , 插入数据库过程在此实现
     * @param joinPoint
     * @return
     */
    @Around(value = "myPointCut()")
    public Object myAround(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object proceed = null;

        try {
            proceed = joinPoint.proceed();
            Log log = new Log();
            int errno = ((BaseRespVo) proceed).getErrno();
            if (errno == 0) {
                Subject subject = SecurityUtils.getSubject();
                Admin admin = (Admin) subject.getPrincipal();
                log.setAdmin(admin.getUsername());
                log.setStatus(true);
            }else {
                log.setStatus(false);
            }

            //获得request 请求头
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            //远端登录的话get这个
            log.setIp(request.getRemoteAddr());
            //本机测试的话用这个
           //log.setIp(request.getLocalAddr());
            String URI = request.getRequestURI();
            if (URI.contains("login")) {
                if (errno != 0) {
                    log.setAdmin("匿名用户");
                }
                log.setAction("登录");
            }
            if (URI.contains("logout")) {
                log.setAction("退出");
            }
            //log表Result存放的是Errmsg
            log.setResult(((BaseRespVo)proceed).getErrmsg());
            log.setType(1);
            log.setAddTime(new Date());
            log.setUpdateTime(new Date());
            log.setDeleted(false);
            logMapper.insertSelective(log);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }



}
