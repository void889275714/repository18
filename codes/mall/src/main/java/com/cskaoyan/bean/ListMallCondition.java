package com.cskaoyan.bean;

import lombok.Data;

@Data
public class ListMallCondition {
    int page;
    int limit;
    String sort;
    String order;
    String keyword;
    String url;
}
