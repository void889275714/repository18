package com.stylefeng.guns.rest.film.vo.film.banner;

import lombok.Data;

import java.io.Serializable;

@Data
public class Top100VO implements Serializable {
    private static final long serialVersionUID = -879635725500230183L;
    String filmId;
    String imgAddress;
    String filmName;
    String score;
}
