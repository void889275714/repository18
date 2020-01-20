package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.stylefeng.guns.rest.cinema.vo.FilmInfo;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;

import com.stylefeng.guns.rest.film.service.FilmServiceAPI;
import com.stylefeng.guns.rest.film.vo.film.banner.*;
import com.stylefeng.guns.rest.film.vo.film.condition.CatVO;
import com.stylefeng.guns.rest.film.vo.film.condition.SourceVO;
import com.stylefeng.guns.rest.film.vo.film.condition.YearVO;
import com.stylefeng.guns.rest.film.vo.film.filmDetail.ActorVO;
import com.stylefeng.guns.rest.film.vo.film.filmDetail.FilmDescVO;
import com.stylefeng.guns.rest.film.vo.film.filmDetail.FilmDetailVO;
import com.stylefeng.guns.rest.film.vo.film.filmDetail.ImgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmServiceAPI.class)
public class FilmServiceImpl implements FilmServiceAPI {

    @Autowired
    private MtimeBannerTMapper bannerTMapper;

    @Autowired
    private MtimeFilmTMapper filmTMapper;

    @Autowired
    private MtimeCatDictTMapper catDictTMapper;

    @Autowired
    private MtimeYearDictTMapper yearDictTMapper;

    @Autowired
    private MtimeSourceDictTMapper sourceDictTMapper;

    @Autowired
    private MtimeFilmInfoTMapper filmInfoTMapper;

    @Autowired
    private MtimeActorTMapper actorTMapper;

    //以下是第一个接口 首页轮播图接口
    @Override
    public List<BannerVO> getBannerVo() {

        List<BannerVO> list = new ArrayList<>();

        EntityWrapper<MtimeBannerT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("is_valid", 0);   //banner需要查是否有效
        List<MtimeBannerT> bannerTS = bannerTMapper.selectList(entityWrapper);
        if (CollectionUtils.isEmpty(bannerTS)) {//查出来是空，就返回一个空的list
            return list;
        }
        for (MtimeBannerT bannerT : bannerTS) {
            BannerVO bannerVo = new BannerVO();
            bannerVo.setBannerId(bannerT.getUuid() + "");
            bannerVo.setBannerAddress(bannerT.getBannerAddress());
            bannerVo.setBannerUrl(bannerT.getBannerUrl());
            list.add(bannerVo);
        }
        return list;
    }

    @Override
    public FilmVO getHotFilmVo(Boolean isLimit, int count, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO hotFilmsVo = new FilmVO();
        ArrayList<FilmInfoVO> list = new ArrayList<>();

        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 1);//正在热映，状态码定义是1
        //获取表中数据条数
        Integer totalCount = filmTMapper.selectCount(entityWrapper);
        List<MtimeFilmT> filmTList;
        int totalPages = 0;
        if (isLimit) {
            //如果是,就限制条数，限制内容为热映影片
            Page page = new Page(1, count);
            filmTList = filmTMapper.selectPage(page, entityWrapper);
        } else {
            Page<MtimeFilmT> page = null;
            //根据sortId来排序，排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId) {
                case 1:
                    page = new Page(nowPage, count, "film_box_office");
                    break;
                case 2:
                    page = new Page(nowPage, count, "film_time");
                    break;
                case 3:
                    page = new Page(nowPage, count, "film_score");
                    break;
                default:
                    //默认用票房来排序
                    page = new Page(nowPage, count, "film_box_office");
                    break;
            }
            //如果不是，则是列表页，同样需要限制内容为热映影片,filmTList = filmTMapper.selectList(entityWrapper);
            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }
            if (catId != 99) {
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }
            filmTList = filmTMapper.selectPage(page, entityWrapper);
            totalPages = (totalCount / count) + 1;//总条数10，用户传是6条每页，因为取整，结果+1

        }

        if (CollectionUtils.isEmpty(filmTList)) {
            return hotFilmsVo;
        }

        //data 转换成返回值需要的类型
        for (MtimeFilmT film : filmTList) {
            FilmInfoVO infoVo = new FilmInfoVO();
            infoVo.setFilmId(film.getUuid() + "");
            infoVo.setFilmType(film.getFilmType());
            infoVo.setFilmAddress(film.getImgAddress());
            infoVo.setFilmName(film.getFilmName());
            //hotFilm独有
            infoVo.setFilmScore(film.getFilmScore());
            list.add(infoVo);
        }

        //添加进返回值里
        hotFilmsVo.setFilmNum(totalCount);
        hotFilmsVo.setFilmInfo(list);
        hotFilmsVo.setNowPage(nowPage);
        hotFilmsVo.setTotalPage(totalPages);
        return hotFilmsVo;
    }

    @Override
    public FilmVO getSoonFilmsVo(Boolean isLimit, int count, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO soonFilmsVo = new FilmVO();
        ArrayList<FilmInfoVO> filmInfoVos = new ArrayList<>();
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 2);

        //获取数量
        Integer totalCounts = filmTMapper.selectCount(entityWrapper);
        //获取分页数量
        List<MtimeFilmT> mtimeFilmTS;
        int totalPages = 0;
        if (isLimit) {
            Page page = new Page(1, count);
            mtimeFilmTS = filmTMapper.selectPage(page, entityWrapper);
        } else {
            Page<MtimeFilmT> page = null;
            //根据sortId来排序，排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId) {
                case 1:
                    page = new Page(nowPage, count, "film_preSaleNum");
                    break;
                case 2:
                    page = new Page(nowPage, count, "film_time");
                    break;
                case 3:
                    page = new Page(nowPage, count, "film_preSaleNum");
                    break;
                default:
                    //默认用票房来排序
                    page = new Page(nowPage, count, "film_preSaleNum");
                    break;
            }
            //如果不是，则是列表页，同样需要限制内容为即将上映影片,filmTList = filmTMapper.selectList(entityWrapper);
            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }
            if (catId != 99) {
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }

            mtimeFilmTS = filmTMapper.selectPage(page, entityWrapper);

            totalPages = (totalCounts / count) + 1;//总条数10，用户传是6条每页，因为取整，结果+1
        }

        if (CollectionUtils.isEmpty(mtimeFilmTS)) {
            return soonFilmsVo;
        }

        for (MtimeFilmT film : mtimeFilmTS) {
            FilmInfoVO infoVo = new FilmInfoVO();
            infoVo.setFilmId(film.getUuid() + "");
            infoVo.setFilmType(film.getFilmType());
            infoVo.setFilmAddress(film.getImgAddress());
            infoVo.setFilmName(film.getFilmName());
            //soonFilm独有
            infoVo.setExpectNum(film.getFilmPresalenum());
            infoVo.setShowTime(film.getFilmTime());
            filmInfoVos.add(infoVo);
        }
        soonFilmsVo.setFilmNum(totalCounts);
        soonFilmsVo.setFilmInfo(filmInfoVos);
        soonFilmsVo.setNowPage(nowPage);
        soonFilmsVo.setTotalPage(totalPages);
        return soonFilmsVo;
    }

    @Override
    public FilmVO getClassicFilms(int count, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfoVO> filmInfoVOS = new ArrayList<>();

        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 3);//3是经典
        //获取分页数量
        List<MtimeFilmT> mtimeFilmTS;
        int totalCounts = filmTMapper.selectCount(entityWrapper);
        int totalPages = 0;
        Page<MtimeFilmT> page = null;
        //根据sortId来排序，排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
        switch (sortId) {
            case 1:
                page = new Page(nowPage, count, "film_box_office");
                break;
            case 2:
                page = new Page(nowPage, count, "film_time");
                break;
            case 3:
                page = new Page(nowPage, count, "film_score");
                break;
            default:
                //默认用票房来排序
                page = new Page(nowPage, count, "film_box_office");
                break;
        }
        if (sourceId != 99) {
            entityWrapper.eq("film_source", sourceId);
        }
        if (yearId != 99) {
            entityWrapper.eq("film_date", yearId);
        }
        if (catId != 99) {
            String catStr = "%#" + catId + "#%";
            entityWrapper.like("film_cats", catStr);
        }
        mtimeFilmTS = filmTMapper.selectPage(page, entityWrapper);
        totalPages = (totalCounts / count) + 1;//总条数10，用户传是6条每页，因为取整，结果+1

        if (CollectionUtils.isEmpty(mtimeFilmTS)) {
            return filmVO;
        }

        for (MtimeFilmT film : mtimeFilmTS) {
            FilmInfoVO infoVo = new FilmInfoVO();
            infoVo.setFilmId(film.getUuid() + "");
            infoVo.setFilmType(film.getFilmType());
            infoVo.setFilmAddress(film.getImgAddress());
            infoVo.setFilmName(film.getFilmName());
            //soonFilm独有
            infoVo.setExpectNum(film.getFilmPresalenum());
            infoVo.setShowTime(film.getFilmTime());
            filmInfoVOS.add(infoVo);
        }
        filmVO.setFilmNum(totalCounts);
        filmVO.setFilmInfo(filmInfoVOS);
        filmVO.setNowPage(nowPage);
        filmVO.setTotalPage(totalPages);
        return filmVO;
    }

    @Override
    public List<BoxRankingVO> getBoxRankingVo(Integer count) {
        List<BoxRankingVO> list = new ArrayList<>();
        //降序分页
        Page<MtimeFilmT> page = new Page(1, count, "film_box_office", false);

        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        List<MtimeFilmT> mtimeFilmTS = filmTMapper.selectPage(page, entityWrapper);
        if (CollectionUtils.isEmpty(mtimeFilmTS)) {
            return list;
        }
        for (MtimeFilmT filmT : mtimeFilmTS) {
            BoxRankingVO vo = new BoxRankingVO();
            vo.setFilmId(filmT.getUuid() + "");
            vo.setImgAddress(filmT.getImgAddress());
            vo.setFilmName(filmT.getFilmName());
            vo.setBoxNum(filmT.getFilmBoxOffice());
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<ExpectRankingVO> getExpectRankingVo(Integer count) {
        List<ExpectRankingVO> list = new ArrayList<>();
        Page<MtimeFilmT> page = new Page(1, count, "film_preSaleNum", false);

        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        List<MtimeFilmT> mtimeFilmTS = filmTMapper.selectPage(page, entityWrapper);
        if (CollectionUtils.isEmpty(mtimeFilmTS)) {
            return list;
        }

        for (MtimeFilmT film : mtimeFilmTS) {
            ExpectRankingVO eVo = new ExpectRankingVO();
            eVo.setFilmId(film.getUuid() + "");
            eVo.setImgAddress(film.getImgAddress());
            eVo.setFilmName(film.getFilmName());
            eVo.setExpectNum(film.getFilmPresalenum());
            list.add(eVo);
        }
        return list;
    }

    @Override
    public List<Top100VO> getTop100Vo(Integer count) {
        List<Top100VO> list = new ArrayList<>();
        Page<MtimeFilmT> page = new Page(1, count, "film_score", false);

        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        List<MtimeFilmT> mtimeFilmTS = filmTMapper.selectPage(page, entityWrapper);
        if (CollectionUtils.isEmpty(mtimeFilmTS)) {
            return list;
        }

        for (MtimeFilmT film : mtimeFilmTS) {
            Top100VO top100Vo = new Top100VO();
            top100Vo.setFilmId(film.getUuid() + "");
            top100Vo.setImgAddress(film.getImgAddress());
            top100Vo.setFilmName(film.getFilmName());
            top100Vo.setScore(film.getFilmScore());
            list.add(top100Vo);
        }
        return list;
    }

    //以下是第二个接口 条件查询接口
    @Override
    public List<CatVO> getCats() {
        List<CatVO> result = new ArrayList<>();
        //按UUID从小到大排序，需要用到entityWrapper
        EntityWrapper<MtimeCatDictT> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderBy("UUID");
        List<MtimeCatDictT> catDictTList = catDictTMapper.selectList(entityWrapper);

        for (MtimeCatDictT cats : catDictTList) {
            CatVO cat = new CatVO();
            cat.setCatId(cats.getUuid() + "");
            cat.setCatName(cats.getShowName());
            result.add(cat);
        }
        return result;
    }

    @Override
    public List<SourceVO> getSources() {
        List<SourceVO> result = new ArrayList<>();
        EntityWrapper<MtimeSourceDictT> entityWrapper = new EntityWrapper<>();
        entityWrapper.groupBy("UUID");
        List<MtimeSourceDictT> sourceDictTS = sourceDictTMapper.selectList(entityWrapper);

        for (MtimeSourceDictT dictT : sourceDictTS) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(dictT.getUuid() + "");
            sourceVO.setSourceName(dictT.getShowName());
            //isActive在Controller判断，这里不用管
            result.add(sourceVO);
        }
        return result;
    }

    @Override
    public List<YearVO> getYears() {
        List<YearVO> result = new ArrayList<>();

        EntityWrapper<MtimeYearDictT> entityWrapper = new EntityWrapper<>();
        entityWrapper.groupBy("UUID");
        List<MtimeYearDictT> yearDictTS = yearDictTMapper.selectList(entityWrapper);

        for (MtimeYearDictT yearDictT : yearDictTS) {
            YearVO yearVO = new YearVO();
            yearVO.setYearId(yearDictT.getUuid() + "");
            yearVO.setYearName(yearDictT.getShowName());
            result.add(yearVO);
        }
        return result;
    }

    @Override
    public FilmDetailVO getFilmDetails(int searchType, String searchParam) {
        //searchType : ‘0表示按照编号查找，1表示按照名称查找’
        FilmDetailVO filmDetailVO = null;
        if (searchType == 1) {
            filmDetailVO = filmTMapper.getFilmDetailByName("%"+searchParam+"%");//模糊匹配传参

        } else  {
            filmDetailVO = filmTMapper.getFilmDetailById(searchParam);
        }
        return filmDetailVO;
    }

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


    @Override
    public FilmInfo getFilmInfoByFieldId(String fieldId) {
        Integer id = Integer.valueOf(fieldId);
        FilmInfo filmInfoById = filmTMapper.getFilmInfoById(id);
        return filmInfoById;
    }
}
