package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bruce
 * @version 1.0.0
 * @date 2020/1/9
 * created by bruce on 2020/1/9 19:29
 */
@Data
public class CinemasRequest<T> implements Serializable {
    private Integer brandId;

    private Integer hallType;

    private Integer areaId;

    private Integer pageSize;

    private Integer nowPage;

}
