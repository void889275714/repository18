package com.stylefeng.guns.rest.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderBuyTickets implements Serializable {
    private static final long serialVersionUID = -4757871283747080402L;

    private String cinemaName;

    private String fieldTime;

    private String filmName;

    private String orderId;

    private String orderPrice;

    private String orderStatus;

    private String orderTimestamp;

    private String seatsName;


}
