package com.cskaoyan.bean;

import lombok.Data;

@Data
public class Paging {
    int page;

    int limit;

    String sort;

    String order;

    String username;

    String mobile;
}
