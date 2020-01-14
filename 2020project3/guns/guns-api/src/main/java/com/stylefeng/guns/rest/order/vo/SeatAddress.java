package com.stylefeng.guns.rest.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SeatAddress implements Serializable {

    private static final long serialVersionUID = 3559774404209010940L;

    private Integer limit;

    private String ids;

    private List<Seat> single;
}
