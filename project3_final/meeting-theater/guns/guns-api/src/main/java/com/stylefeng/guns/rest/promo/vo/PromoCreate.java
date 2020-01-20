package com.stylefeng.guns.rest.promo.vo;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.Data;

import java.io.Serializable;

@Data
public class PromoCreate implements Serializable {

    private static final long serialVersionUID = 8694526981388350095L;

    //秒杀活动Id
    private String promoId;

    //购买数量
    private String amount;
}
