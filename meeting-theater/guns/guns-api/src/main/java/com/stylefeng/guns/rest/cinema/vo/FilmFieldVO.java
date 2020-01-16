package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmFieldVO implements Serializable {

    private static final long serialVersionUID = 6639986346069145013L;

    private String beginTime;

    private String endTime;

    private Integer fieldId;

    private String hallName;

    private String language;

    private Integer price;
}
