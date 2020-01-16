package com.stylefeng.guns.rest;

import lombok.Data;

@Data
public class ResponseVO {
    Integer status;

    String msg;

    Object Data;

    String imgPre;

    String nowPage;

    String totalPage;

}
