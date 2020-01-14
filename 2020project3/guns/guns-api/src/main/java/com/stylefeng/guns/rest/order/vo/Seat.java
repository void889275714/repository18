package com.stylefeng.guns.rest.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Seat implements Serializable {

    private static final long serialVersionUID = -2345994635279508352L;

    private Integer seatId;

    private Integer row;

    private Integer column;
}
