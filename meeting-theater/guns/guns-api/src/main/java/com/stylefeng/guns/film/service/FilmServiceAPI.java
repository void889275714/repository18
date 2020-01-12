package com.stylefeng.guns.film.service;


import com.stylefeng.guns.vo.film.banner.*;
import com.stylefeng.guns.vo.film.condition.CatVO;
import com.stylefeng.guns.vo.film.condition.SourceVO;
import com.stylefeng.guns.vo.film.condition.YearVO;
import com.stylefeng.guns.vo.film.filmDetail.ActorVO;
import com.stylefeng.guns.vo.film.filmDetail.FilmDescVO;
import com.stylefeng.guns.vo.film.filmDetail.FilmDetailVO;
import com.stylefeng.guns.vo.film.filmDetail.ImgVO;

import java.util.List;

public interface FilmServiceAPI {
    /*
    第一个接口 6个data
    1.banners
    2.hotFilms
    3.soonFilms
    4.boxRanking
    5.expectRanking
    6.top100
    */

    List<BannerVO> getBannerVo();

    //count显示数量，isLimit是否显示全部
    //函数重构，正式项目中复制一个改变形参才行，但本次项目有预留，所以直接在原来的新参中添加
    FilmVO getHotFilmVo(Boolean isLimit, int count, int nowPage, int sortId, int sourceId, int yearId, int catId);

    FilmVO getSoonFilmsVo(Boolean isLimit, int count, int nowPage, int sortId, int sourceId, int yearId, int catId);

    FilmVO getClassicFilms(int count, int nowPage, int sourceId, int sortId, int yearId, int catId);

    List<BoxRankingVO> getBoxRankingVo(Integer count);

    List<ExpectRankingVO> getExpectRankingVo(Integer count);

    List<Top100VO> getTop100Vo(Integer count);

    /*
    第二个接口
    1.catInfo
    2.sourceInfo
    3.yearInfo
     */
    List<CatVO> getCats();

    List<SourceVO> getSources();

    List<YearVO> getYears();

    /*
    第三个接口
    1.info04以上分为一个部分
    2.info04
     */
    FilmDetailVO getFilmDetails(int searchType, String searchParam);
    //获取影片描述信息
    FilmDescVO getFilmDesc(String filmId);
    //获取图片信息
    ImgVO getImgs(String filmId);
    //获取导演信息
    ActorVO getDectInfo(String filmId);
    //获取演员信息
    List<ActorVO> getActors(String filmId);

}
