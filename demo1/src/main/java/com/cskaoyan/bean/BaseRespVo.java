package com.cskaoyan.bean;

import lombok.Data;



@Data
public class BaseRespVo<T> {
    T data;

    int errno;

    String errmsg;

    public static BaseRespVo ok(Object data){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }
}
