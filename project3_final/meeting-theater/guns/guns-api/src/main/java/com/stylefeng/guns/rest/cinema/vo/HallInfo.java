package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class HallInfo implements Serializable {

    private static final long serialVersionUID = 8955756975220182610L;
    private Integer hallFieldId;
    private String hallName;
    private Integer price;
    private String seatFile;
    private String soldSeats;
    private String discountPrice;
}
