package com.cskaoyan.bean;

import lombok.Data;

/**
 * @Description TODO
 * @Date 2019-12-27 10:19
 * @Created by ouyangfan
 */
@Data
public class ListCondition {
    int page;
    int limit;
    String sort;
    String order;
    /**
     * 用于商品搜索，非模糊查询
     * 商品编号
     */
    String goodsSn;
    /**
     * 用于商品搜索，模糊查询
     * 商品名
     */
    String name;

    String keyword;
    String url;
    String mobile;
    String username;
    String userId;
    String valueId;
    String goodsId;
    String id;
}
