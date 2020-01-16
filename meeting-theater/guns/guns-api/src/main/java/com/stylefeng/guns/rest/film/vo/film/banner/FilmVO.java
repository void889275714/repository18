package com.stylefeng.guns.rest.film.vo.film.banner;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmVO implements Serializable {
    private static final long serialVersionUID = 8202380960673440377L;
    private Integer filmNum;
    private List<FilmInfoVO> filmInfo;
    private int nowPage;
    private int totalPage;

}
