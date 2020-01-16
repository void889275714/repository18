package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaHallType implements Serializable {

    private static final long serialVersionUID = 3504622395865827779L;

    private Integer halltypeId;

    private String halltypeName;

    private boolean active;
}
