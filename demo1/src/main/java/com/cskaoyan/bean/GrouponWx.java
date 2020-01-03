package com.cskaoyan.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GrouponWx {
    private Integer id;
    private Integer orderId;
    private Integer rulesId;
    private Integer userId;
    private Integer grouponId;
    private Integer creatorUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String shareUrl;
    private  Integer payed;
    private Integer deleted;

}
