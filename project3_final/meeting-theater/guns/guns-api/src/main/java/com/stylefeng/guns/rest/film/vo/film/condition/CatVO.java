package com.stylefeng.guns.rest.film.vo.film.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatVO implements Serializable {
    private static final long serialVersionUID = 5336521562735688660L;
    private String catId;
    private String CatName;
    private boolean isActive;

}
