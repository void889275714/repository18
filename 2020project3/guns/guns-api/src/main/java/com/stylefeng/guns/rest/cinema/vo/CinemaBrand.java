package com.stylefeng.guns.rest.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaBrand implements Serializable {

    private static final long serialVersionUID = -4843107057684852549L;

    private Integer brandId;

    private String brandName;

    private boolean active;

}
