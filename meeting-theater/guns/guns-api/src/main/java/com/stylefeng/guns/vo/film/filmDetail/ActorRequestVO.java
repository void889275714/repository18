package com.stylefeng.guns.vo.film.filmDetail;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
    封装info1-3和info4的
 */
@Data
public class ActorRequestVO implements Serializable {
    private static final long serialVersionUID = -4318512746937532386L;
    private ActorVO director;
    private List<ActorVO> actors;
}
