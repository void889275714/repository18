package com.cskaoyan.bean;

import lombok.Data;

import java.util.List;

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
    int size;
    String categoryId;
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
    int couponId;
    int type;
    String status;
    String title;
    String subtitle;
    String content;
    String orderSn;
    List<Short> orderStatusArray;
}
