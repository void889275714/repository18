package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bruce
 * @version 1.0.0
 * @date 2020/1/9
 * created by bruce on 2020/1/9 19:32
 */
@Data
public class BaseRespVo<T> implements Serializable {
    private static final long serialVersionUID = 6260235445464912224L;
    private T data;
//    private String imgPre;
//    private String msg;
    private Integer nowPage;
    private Integer status;
    private Integer totalPage;

    public static <V> BaseRespVo<V> success(V data) {
        BaseRespVo<V> baseRespVo = new BaseRespVo<>();
        baseRespVo.setStatus(0);
        baseRespVo.setData(data);
        //baseRespVo.setImgPre("http://img.meetingshop.cn/");
        return baseRespVo;
    }

    public static <V> BaseRespVo<V> error() {
        BaseRespVo<V> baseRespVo = new BaseRespVo<>();
        baseRespVo.setStatus(1);
       // baseRespVo.setMsg("查询失败，无banner可加载");
        return baseRespVo;
    }

}
