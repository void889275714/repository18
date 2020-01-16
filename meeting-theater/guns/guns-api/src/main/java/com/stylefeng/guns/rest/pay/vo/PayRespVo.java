package com.stylefeng.guns.rest.pay.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class PayRespVo implements Serializable {

    private String orderId;

    private Integer orderStatus;

    private String orderMsg;

}
