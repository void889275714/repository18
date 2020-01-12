package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.vo.film.banner.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmIndexVO implements Serializable {
    private static final long serialVersionUID = 6591270543433287957L;
    private List<BannerVO> banners;
    private FilmVO hotFilms;
    private FilmVO soonFilms;
    private List<BoxRankingVO> boxRanking;
    private List<ExpectRankingVO> expectRanking;
    private List<Top100VO> top100;
}
