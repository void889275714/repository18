package com.stylefeng.guns.rest.film.vo.film.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class SourceVO implements Serializable {
    private static final long serialVersionUID = -5088213145168413823L;
    private String sourceId;
    private String sourceName;
    private Boolean isActive;
}
