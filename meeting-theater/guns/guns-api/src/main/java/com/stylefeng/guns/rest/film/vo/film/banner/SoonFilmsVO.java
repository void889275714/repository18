package com.stylefeng.guns.rest.film.vo.film.banner;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SoonFilmsVO implements Serializable {

    private static final long serialVersionUID = -4233178417368523841L;
    private Integer filmNum;
    private List<FilmInfoVO> filmInfo;
    private int nowPage;
    private int totalPage;
}
