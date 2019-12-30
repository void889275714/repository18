package com.cskaoyan.bean;

import lombok.Data;

import java.util.List;

@Data
public class ListCondition {
    /**
     * 用户管理页面需要的请求参数 page,limit,sort,order,username,mobile
     */
    int page;

    int limit;

    String sort;

    String order;

    String username;

    String mobile;

    String name;

    String userId;
    /**
     * 这是会员收藏的商品ID
     */
    String valueId;

    /**
     * 意见反馈需要的反馈id参数
     */
    String id;

    /**
     * 会员足迹的商品ID
     */
    String goodsId;

    /**
     * 搜索历史的关键字
     */
    String keyword;

    /**
     *订单编号
     */
    String orderSn;

    /**
     * 订单状态
     */
    List<Short> orderStatusArray;
}
