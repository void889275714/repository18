package com.stylefeng.guns.rest.cinema;

import com.stylefeng.guns.rest.cinema.vo.BaseRespVo;
import com.stylefeng.guns.rest.cinema.vo.CinemaBrandAreaHall;
import com.stylefeng.guns.rest.cinema.vo.CinemasRequest;
import com.stylefeng.guns.rest.cinema.vo.FieldInfoVO;

import java.util.Map;

public interface CinemaService {

    public FieldInfoVO getFieldInfo(String cinemaId, String fieldId);

    public CinemaBrandAreaHall getCinemaCondition(Integer brandId,String hallType,Integer areaId);

    public Map<String,Object> getCinemaField(String cinemaId);

    public BaseRespVo getCinemasInfos(CinemasRequest cinemasRequest);
}
