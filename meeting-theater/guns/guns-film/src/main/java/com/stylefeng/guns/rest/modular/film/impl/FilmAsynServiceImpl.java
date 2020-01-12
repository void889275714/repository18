package com.stylefeng.guns.rest.modular.film.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.film.service.FilmAsynServiceAPI;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeActorTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmInfoTMapper;

import com.stylefeng.guns.rest.common.persistence.model.MtimeActorT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmInfoT;
import com.stylefeng.guns.vo.film.filmDetail.ActorVO;
import com.stylefeng.guns.vo.film.filmDetail.FilmDescVO;
import com.stylefeng.guns.vo.film.filmDetail.FilmDetailVO;
import com.stylefeng.guns.vo.film.filmDetail.ImgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Service
public class FilmAsynServiceImpl implements FilmAsynServiceAPI {

    @Autowired
    private MtimeFilmInfoTMapper filmInfoTMapper;

    @Autowired
    private MtimeActorTMapper actorTMapper;

    private MtimeFilmInfoT getFilmInfo(String filmId) {
        MtimeFilmInfoT filmInfoVO = new MtimeFilmInfoT();
        filmInfoVO.setFilmId(filmId);
        filmInfoVO = filmInfoTMapper.selectOne(filmInfoVO);
        return filmInfoVO;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MtimeFilmInfoT mtimeFilmInfoT = new MtimeFilmInfoT();
        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(mtimeFilmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);
        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        MtimeFilmInfoT filmInfoT = getFilmInfo(filmId);
        //图片地址根据数据库查询，是5个以逗号分隔的URL
        String[] split = filmInfoT.getFilmImgs().split(",");
        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(split[0]);
        imgVO.setImg01(split[1]);
        imgVO.setImg02(split[2]);
        imgVO.setImg03(split[3]);
        imgVO.setImg04(split[4]);
        return imgVO;
    }

    @Override
    public ActorVO getDectInfo(String filmId) {
        MtimeFilmInfoT filmInfoT = getFilmInfo(filmId);
        //获取导演编号
        Integer directorId = filmInfoT.getDirectorId();

        MtimeActorT mtimeActorT = actorTMapper.selectById(directorId);

        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(mtimeActorT.getActorImg());
        actorVO.setDirectorName(mtimeActorT.getActorName());
        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        List<ActorVO> actors = actorTMapper.getActors(filmId);
        return actors;
    }



}
