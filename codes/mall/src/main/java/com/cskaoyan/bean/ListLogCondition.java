package com.cskaoyan.bean;

import lombok.Data;

@Data
public class ListLogCondition {
    int page;
    int limit;
    String sort;
    String order;
    String name;
}
