package com.stylefeng.guns.rest.film.service;




import com.stylefeng.guns.rest.film.vo.film.filmDetail.ActorVO;
import com.stylefeng.guns.rest.film.vo.film.filmDetail.FilmDescVO;
import com.stylefeng.guns.rest.film.vo.film.filmDetail.ImgVO;

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
