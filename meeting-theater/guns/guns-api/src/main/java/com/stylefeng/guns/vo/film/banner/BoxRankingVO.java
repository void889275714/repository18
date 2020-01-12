package com.stylefeng.guns.vo.film.banner;

import lombok.Data;

import java.io.Serializable;

@Data
public class BoxRankingVO implements Serializable {

    private static final long serialVersionUID = 2436116441944324452L;
    String filmId;
    String imgAddress;
    String filmName;
    Integer boxNum;
}
