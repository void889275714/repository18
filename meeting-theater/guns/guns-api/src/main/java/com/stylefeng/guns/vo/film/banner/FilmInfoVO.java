package com.stylefeng.guns.vo.film.banner;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FilmInfoVO implements Serializable {

    private static final long serialVersionUID = -5073573115955901240L;
    private  String filmId;
    private Integer filmType;
    private String filmAddress;
    private String filmName;
    private String filmScore;
    private Integer expectNum;
    private Date showTime;
}
