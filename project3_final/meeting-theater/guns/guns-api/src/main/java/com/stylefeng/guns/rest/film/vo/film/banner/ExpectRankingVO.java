package com.stylefeng.guns.rest.film.vo.film.banner;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExpectRankingVO implements Serializable {

    private static final long serialVersionUID = -2138469699882440540L;
    String filmId;
    String imgAddress;
    String filmName;
    Integer expectNum;

}
