package com.stylefeng.guns.rest.film.vo.film.filmDetail;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActorVO implements Serializable {

    private static final long serialVersionUID = 5765746450596952797L;
    private String imgAddress;
    private String directorName ;
    private String roleName ;
}
