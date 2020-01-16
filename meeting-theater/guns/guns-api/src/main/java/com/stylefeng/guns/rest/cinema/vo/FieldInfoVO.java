package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * cinema/getFieldInfo  要返回的 data 里面包含的信息
 */
@Data
public class FieldInfoVO implements Serializable {
    private static final long serialVersionUID = 7063430292072658896L;
    //成员变量名字对应上
    private FilmInfo filmInfo;

    private CinemaInfo cinemaInfo;

    private HallInfo hallInfo;

}
