package com.stylefeng.guns.rest.promo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromoStatus implements Serializable {

    private static final long serialVersionUID = 3170311848356399891L;

    private Integer index;

    private String description;

    public PromoStatus(Integer index, String description) {
        this.index = index;
        this.description = description;
    }

    public static PromoStatus NOT_START = new PromoStatus(0,"未开始");

    public static PromoStatus ING = new PromoStatus(1,"进行中");

    public static PromoStatus ENDED = new PromoStatus(2,"已结束");

}
