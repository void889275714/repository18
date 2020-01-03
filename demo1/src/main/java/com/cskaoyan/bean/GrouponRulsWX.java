package com.cskaoyan.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GrouponRulsWX {
    Integer id;
    Integer goodsId;
    String goodsName;
    String picUrl;
    Integer discount;
    Integer discountMember;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date addTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date expireTime;
    Integer deleted;


}
