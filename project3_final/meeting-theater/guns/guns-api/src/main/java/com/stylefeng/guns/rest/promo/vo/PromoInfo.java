package com.stylefeng.guns.rest.promo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PromoInfo implements Serializable {

    private static final long serialVersionUID = -8971804417385011581L;

    private String cinemaAddress;

    private String cinemaId;

    private String cinemaName;

    private String description;

    private String endTime;

    private String imgAddress;

    private BigDecimal price;

    private String startTime;

    private Integer status;

    private Integer stock;

    private Integer uuid;

}
