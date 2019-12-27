package com.cskaoyan.bean;

import lombok.Data;

@Data
public class ListCondition {
    int page;
    int limit;
    String sort;
    String order;
    String keyword;
    String url;
}
