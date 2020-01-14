package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.film.FilmService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.dc.pr.PRError;

import java.util.*;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {


    @Reference
    private FilmService filmService;

    @Autowired
    private MtimeCinemaTMapper cinemaTMapper;

    @Autowired
    private MtimeFieldTMapper mtimeFieldTMapper;

    /*-------------------------------4、获取场次详细信息接口------------------------------------*/

    /**
     * 在ServiceImpl中查找数据库中的数据拼接成data
     * @param cinemaId
     * @param fieldId
     * @return
     */
    @Override
    public FieldInfoVO getFieldInfo(String cinemaId, String fieldId) {
        FieldInfoVO fieldInfoVO = new FieldInfoVO();
        FilmInfo filmInfo = getFilmInfo(cinemaId,fieldId);
        CinemaInfo cinemaInfo = getCinemaInfo(cinemaId,fieldId);
        HallInfo hallInfo = getHallInfo(cinemaId,fieldId);

        fieldInfoVO.setCinemaInfo(cinemaInfo);
        fieldInfoVO.setFilmInfo(filmInfo);
        fieldInfoVO.setHallInfo(hallInfo);
        return fieldInfoVO;
    }


    private HallInfo getHallInfo(String cinemaId, String fieldId) {
        HallInfo hallInfo = mtimeFieldTMapper.getHallInfo(fieldId);
        return hallInfo;
    }

    private CinemaInfo getCinemaInfo(String cinemaId, String fieldId) {
        MtimeCinemaT mtimeCinemaT = cinemaTMapper.selectById(cinemaId);
        CinemaInfo cinemaInfo = convert2CinemaInfo(mtimeCinemaT);
        return cinemaInfo;
    }

    /**
     * 将系统生成的cinema转化为前端需要的信息
     * @param mtimeCinemaT
     * @return
     */
    private CinemaInfo convert2CinemaInfo(MtimeCinemaT mtimeCinemaT) {
        CinemaInfo cinemaInfo = new CinemaInfo();
        cinemaInfo.setCinemaId(mtimeCinemaT.getUuid());
        cinemaInfo.setCinemaAdress(mtimeCinemaT.getCinemaAddress());
        cinemaInfo.setCinemaName(mtimeCinemaT.getCinemaName());
        cinemaInfo.setCinemaPhone(mtimeCinemaT.getCinemaPhone());
        cinemaInfo.setImgUrl(mtimeCinemaT.getImgAddress());
        return cinemaInfo;
    }

    private FilmInfo getFilmInfo(String cinemaId, String fieldId) {
        FilmInfo filmInfo = new FilmInfo();
        filmInfo = filmService.getFilmInfoByFieldId(fieldId);
        return filmInfo;
    }


    /*--------------------------------2、获取影院列表查询条件------------------------------------*/

    @Autowired
    MtimeAreaDictTMapper areaDictTMapper;

    @Autowired
    MtimeBrandDictTMapper brandDictTMapper;

    @Autowired
    MtimeHallDictTMapper hallDictTMapper;



    @Override
    public CinemaBrandAreaHall getCinemaCondition(Integer brandId, String hallType, Integer areaId) {
        //返回的data
        CinemaBrandAreaHall cinemaBrandAreaHall = new CinemaBrandAreaHall();

        //拿到area 数据的list
        EntityWrapper<MtimeAreaDictT> areaWrapper = new EntityWrapper<>();

        List<MtimeAreaDictT> mtimeAreaDictTS = areaDictTMapper.selectList(areaWrapper);

        List<CinemaArea> cinemaAreaList = new ArrayList<>();
        for (MtimeAreaDictT mtimeAreaDictT : mtimeAreaDictTS) {
            CinemaArea cinemaArea = new CinemaArea();
            cinemaArea.setAreaId(mtimeAreaDictT.getUuid());
            cinemaArea.setAreaName(mtimeAreaDictT.getShowName());
            if (mtimeAreaDictT.getUuid() != areaId) {
                    cinemaArea.setActive(false);
              }else {
                cinemaArea.setActive(true);
            }
            cinemaAreaList.add(cinemaArea);
        }

        cinemaBrandAreaHall.setAreaList(cinemaAreaList);


        //拿到brand 数据的list
        EntityWrapper<MtimeBrandDictT> brandWrapper = new EntityWrapper<>();

        List<MtimeBrandDictT> mtimeBrandDictTS = brandDictTMapper.selectList(brandWrapper);

        List<CinemaBrand> brandList = new ArrayList<>();

        for (MtimeBrandDictT mtimeBrandDictT : mtimeBrandDictTS) {
            CinemaBrand cinemaBrand = new CinemaBrand();
            cinemaBrand.setBrandId(mtimeBrandDictT.getUuid());
            cinemaBrand.setBrandName(mtimeBrandDictT.getShowName());
            if (mtimeBrandDictT.getUuid() != brandId) {
                cinemaBrand.setActive(false);
            }else {
                cinemaBrand.setActive(true);
            }
            brandList.add(cinemaBrand);
        }

        cinemaBrandAreaHall.setBrandList(brandList);

        //拿到hall 数据的list
        EntityWrapper<MtimeHallDictT> halltypeWrapper = new EntityWrapper<>();

        List<MtimeHallDictT> mtimeHallDictTS = hallDictTMapper.selectList(halltypeWrapper);

        ArrayList<CinemaHallType> hallTypeList = new ArrayList<>();
        for (MtimeHallDictT mtimeHallDictT : mtimeHallDictTS) {
            CinemaHallType cinemaHallType = new CinemaHallType();
            cinemaHallType.setHalltypeId(mtimeHallDictT.getUuid());
            cinemaHallType.setHalltypeName(mtimeHallDictT.getShowName());
            if (mtimeHallDictT.getUuid() != Integer.valueOf(hallType)){
                cinemaHallType.setActive(false);
            }else {
                cinemaHallType.setActive(true);
            }
            hallTypeList.add(cinemaHallType);
        }

        cinemaBrandAreaHall.setHalltypeList(hallTypeList);

        return cinemaBrandAreaHall;
    }

    /*--------------------------------3、获取播放场次接口------------------------------------*/

    @Autowired
    MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;


    /**
     * data里需要两个
     * 一个cinemaInfo
     * 一个List 影院详情列表
     * @param cinemaId
     * @return
     */
    @Override
    public Map<String, Object> getCinemaField(String cinemaId) {
        Map<String, Object> map = new HashMap<>();
        CinemaInfo cinemaInfo = getCinemaInfo(cinemaId,"0");
        map.put("cinemaInfo",cinemaInfo);
        ArrayList<CinemaFilm> cinemaFilmArrayList = new ArrayList<>();


        //CinemaFilm 需要 filmFields  获取总信息
        EntityWrapper<MtimeFieldT> fieldTEntityWrapper = new EntityWrapper<>();
        fieldTEntityWrapper.eq("cinema_id",cinemaId);
        List<MtimeFieldT> mtimeFieldTS = mtimeFieldTMapper.selectList(fieldTEntityWrapper);

        //不同的filmId有不同的List, 不同的filmId值也是创建不同的 CinemaFilm对象
        Set<Integer> filmIdSet = new HashSet<>();
        for (MtimeFieldT mtimeFieldT : mtimeFieldTS) {
            Integer filmId = mtimeFieldT.getFilmId();
            filmIdSet.add(filmId);
        }
        int size = filmIdSet.size();

        //对象的拼接在这里面操作

        for (int i = 0; i < size; i++) {
            //把取到的 mtimeFieldTS 里面的信息转换到 fieldVOArrayList 里面
            ArrayList<Integer> integers = new ArrayList<>(filmIdSet);
            Integer integer = integers.get(i);

            CinemaFilm cinemaFilm = new CinemaFilm();
            ArrayList<FilmFieldVO> fieldVOArrayList = new ArrayList<>();
            //hallfilminfo
            EntityWrapper<MtimeHallFilmInfoT> hallFilmInfoTEntityWrapper = new EntityWrapper<>();
            hallFilmInfoTEntityWrapper.eq("film_id", integer);
            List<MtimeHallFilmInfoT> mtimeHallFilmInfoTS = mtimeHallFilmInfoTMapper.selectList(hallFilmInfoTEntityWrapper);
            //外层信息
            MtimeHallFilmInfoT filmIdHallInfo = mtimeHallFilmInfoTS.get(0);


            //把信息封装到内层List中
            for (MtimeFieldT mtimeFieldT : mtimeFieldTS) {
                if (mtimeFieldT.getFilmId() == integer){
                    FilmFieldVO filmFieldVO = new FilmFieldVO();
                    filmFieldVO.setBeginTime(mtimeFieldT.getBeginTime());
                    filmFieldVO.setEndTime(mtimeFieldT.getEndTime());
                    filmFieldVO.setFieldId(mtimeFieldT.getHallId());
                    filmFieldVO.setHallName(mtimeFieldT.getHallName());
                    filmFieldVO.setPrice(mtimeFieldT.getPrice());
                    filmFieldVO.setLanguage(filmIdHallInfo.getFilmLanguage());
                    fieldVOArrayList.add(filmFieldVO);
                }
            }
            //封装外层对象第一个信息
            cinemaFilm.setFilmFields(fieldVOArrayList);
            cinemaFilm.setActors(filmIdHallInfo.getActors());
            cinemaFilm.setFilmCats(filmIdHallInfo.getFilmCats());
            cinemaFilm.setFilmId(String.valueOf(integer));
            cinemaFilm.setFilmLength(filmIdHallInfo.getFilmLength());
            cinemaFilm.setFilmName(filmIdHallInfo.getFilmName());
            cinemaFilm.setFilmType(filmIdHallInfo.getFilmLanguage());
            cinemaFilm.setImgAddress(filmIdHallInfo.getImgAddress());

            cinemaFilmArrayList.add(cinemaFilm);
        }


        map.put("filmList",cinemaFilmArrayList);
        return map;
    }





    /*--------------------------------1、获取影院信息接口------------------------------------*/

    /***
     * 查询影院的信息
     * @param cinemasRequest
     * @return
     */
    @Override
    public BaseRespVo getCinemasInfos(CinemasRequest cinemasRequest) {
        BaseRespVo baseRespVo = new BaseRespVo();
        PageHelper.startPage(cinemasRequest.getNowPage(),cinemasRequest.getPageSize());
        List<CinemaMsg> cinemasInfosRespVoList = cinemaTMapper.getCinemasInfos(cinemasRequest);
        baseRespVo.setData(cinemasInfosRespVoList);
        baseRespVo.setNowPage(cinemasRequest.getNowPage());
        baseRespVo.setTotalPage(5);
        baseRespVo.setStatus(0);
        return baseRespVo;
    }

}
