package com.stylefeng.guns.rest.film.vo.film.filmDetail;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmDescVO  implements Serializable {
    private static final long serialVersionUID = -8358280459684860937L;
    private String biography;
    private String filmId;
}
