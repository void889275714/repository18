package com.cskaoyan.bean;

import lombok.Data;

@Data
public class ListGrCondition {
    int page;
    int limit;
    String order;
    String sort;
    int goodsId;
}
