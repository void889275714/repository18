package com.cskaoyan.bean;

import lombok.Data;

@Data
public class ListBrandCondition {
    int page;
    int limit;
    String sort;
    String order;
    int id;
    String name;
}
