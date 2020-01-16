package com.stylefeng.guns.rest.promo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class PromoListCondition implements Serializable {

    private static final long serialVersionUID = 7795771986797118445L;

    private String brandId;

    private String hallType;

    private String areaId;

    private String pageSize;

    private String nowPage;

}
