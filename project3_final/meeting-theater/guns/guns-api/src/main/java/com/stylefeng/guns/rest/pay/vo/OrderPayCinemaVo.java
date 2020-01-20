package com.stylefeng.guns.rest.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPayCinemaVo implements Serializable {

    private Integer cinemaId;

    private String cinemaName;

    private String price;

}
