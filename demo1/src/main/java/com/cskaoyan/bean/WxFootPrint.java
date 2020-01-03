package com.cskaoyan.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WxFootPrint {

    String brief;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    Date addTime;

    int goodsId;

    int id;

    String name;

    String picUrl;

    BigDecimal retailPrice;
}
