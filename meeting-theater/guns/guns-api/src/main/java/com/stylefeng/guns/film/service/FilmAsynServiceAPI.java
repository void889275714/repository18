package com.stylefeng.guns.film.service;


import com.stylefeng.guns.vo.film.condition.CatVO;
import com.stylefeng.guns.vo.film.condition.SourceVO;
import com.stylefeng.guns.vo.film.condition.YearVO;
import com.stylefeng.guns.vo.film.filmDetail.ActorVO;
import com.stylefeng.guns.vo.film.filmDetail.FilmDescVO;
import com.stylefeng.guns.vo.film.filmDetail.FilmDetailVO;
import com.stylefeng.guns.vo.film.filmDetail.ImgVO;

import java.util.List;

public interface FilmAsynServiceAPI {

    /*
    第三个接口
    1.info04以上分为一个部分
    2.info04
     */
    //获取影片描述信息
    FilmDescVO getFilmDesc(String filmId);
    //获取图片信息
    ImgVO getImgs(String filmId);
    //获取导演信息
    ActorVO getDectInfo(String filmId);
    //获取演员信息
    List<ActorVO> getActors(String filmId);

}
