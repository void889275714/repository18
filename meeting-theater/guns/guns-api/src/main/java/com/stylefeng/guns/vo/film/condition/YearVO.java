package com.stylefeng.guns.vo.film.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class YearVO implements Serializable {
    private static final long serialVersionUID = -1870347995937363471L;
    private String yearId;
    private String yearName;
    private Boolean isActive;
}
