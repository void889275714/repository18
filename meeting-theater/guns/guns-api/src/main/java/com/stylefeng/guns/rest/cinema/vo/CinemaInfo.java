package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class CinemaInfo implements Serializable {
    private static final long serialVersionUID = 6325344119821276860L;
    private Integer cinemaId;
    private String imgUrl;
    private String cinemaName;
    private String cinemaAdress;
    private String cinemaPhone;
}
