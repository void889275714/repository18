package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class CinemaArea implements Serializable {

    private static final long serialVersionUID = -6109522446251742044L;

    private Integer areaId;

    private String areaName;

    private boolean active;
}
