package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bruce
 * @version 1.0.0
 * @date 2020/1/8
 * created by bruce on 2020/1/8 20:17
 */
@Data
public class CinemaMsg implements Serializable {

    private static final long serialVersionUID = 1569340610008841840L;
    private Integer uuid;

    private String cinemaName;

    private String address;

    private int minimumPrice;

}
