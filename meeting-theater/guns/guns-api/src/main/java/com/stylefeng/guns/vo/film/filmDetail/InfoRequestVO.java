package com.stylefeng.guns.vo.film.filmDetail;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfoRequestVO implements Serializable {
    private static final long serialVersionUID = -3513350886151609872L;
    private String biography;
    private ActorRequestVO actors;
    private ImgVO imgVO;
    private String filmId;
}
