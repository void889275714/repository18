package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CinemaBrandAreaHall implements Serializable {

    private static final long serialVersionUID = -3536912439030928716L;
    //第一个对象列表
    List<CinemaBrand> brandList;

    List<CinemaArea> areaList;

    List<CinemaHallType> halltypeList;

}
