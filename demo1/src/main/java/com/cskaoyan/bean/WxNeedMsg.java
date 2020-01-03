package com.cskaoyan.bean;


import lombok.Data;



@Data
public class WxNeedMsg {

    private Integer groupon_price;
    private Integer groupon_member;
    private GoodsGrouponWx goods;
}
