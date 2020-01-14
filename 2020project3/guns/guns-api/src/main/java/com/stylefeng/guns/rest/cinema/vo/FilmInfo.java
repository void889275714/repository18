package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class FilmInfo implements Serializable {
    private static final long serialVersionUID = 6799268807407065740L;
    private String filmId;
    private String filmName;
    private String filmLength;
    private String filmType;
    private String filmCats;
    private String actors;
    private String imgAddress;
    //private List<FilmFieldVO> filmFields;
}
