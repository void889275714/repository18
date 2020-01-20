package com.stylefeng.guns.rest.film.vo.film.filmDetail;


import lombok.Data;

@Data
public class ResponseVO<T>
{
    //返回状态
    private int status;
    //返回消息
    private String Msg;
    //返回的数据实体
    private T data;
    //返回各种pre
    private String imgPre;
    //分页使用
    private int nowPage;
    private int totalPage;

    public static<M> ResponseVO success(int nowPage, int totalPage, String imgPre, M m){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);
        responseVO.setTotalPage(totalPage);
        responseVO.setNowPage(nowPage);

        return responseVO;
    }

    //一般成功
    public static <T> ResponseVO success(T t)
    {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setImgPre("http://img.meetingshop.cn");
        responseVO.setStatus(0);
        responseVO.setData(t);
        return responseVO;
    }

    //注册成功
    public static <T> ResponseVO success(String message)
    {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(message);
        return responseVO;
    }

    //影片首页成功
    public static <T> ResponseVO success(String imgPre, T t)
    {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setImgPre(imgPre);
        responseVO.setData(t);
        return responseVO;
    }

    //影片条件查询成功
    public static <T> ResponseVO success(String imgPre, T t, int nowPage, int totalPage)
    {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setImgPre(imgPre);
        responseVO.setData(t);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);
        return responseVO;
    }

    //业务异常
    public static <T> ResponseVO serviceFail(String msg)
    {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);
        return responseVO;
    }

    //系统异常
    public static <T> ResponseVO systemFail(String msg)
    {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);
        return responseVO;
    }


    //私有化,不允许外部实例化
    private ResponseVO()
    {
    }

}
