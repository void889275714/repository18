package com.cskaoyan.bean;

import lombok.Data;

@Data
public class WxGrouponMsg {

    private  Integer id;
    private String name ;
    private String brief;
    private String picUrl;
    private Integer counterPrice;
    private Integer retailPrice;
}

