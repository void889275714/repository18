package com.stylefeng.guns.rest.film.vo.film.filmDetail;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmDetailVO implements Serializable {

    private static final long serialVersionUID = 8322384978027731136L;
    //dubbo异步请求，需要filmId
    private String filmId;
    private String filmName;
    private String filmEnName;
    private String imgAddress;
    private String score;
    private String scoreNum;
    private String totalBox;
    private String info01;
    private String info02;
    private String info03;

    private InfoRequestVO info04;

}
