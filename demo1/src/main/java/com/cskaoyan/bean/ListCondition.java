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

    /**
     * wx端的goods需要的变量
     */
    int size;

    int categoryId;

    int brandId;

    String goodsSn;


    /**
     * 用于商品搜索，非模糊查询
     * 商品编号
     */

    /**
     * 用于商品搜索，模糊查询
     * 商品名
     */

    String url;

    int couponId;
    int type;
    String status;
    String title;
    String subtitle;
    String content;
    Integer pid;







    /**
     * 用于商品搜索，非模糊查询
     * 商品编号
     */

    /**
     * 用于商品搜索，模糊查询
     * 商品名
     */

    /**
     * 用于订单显示，showType表示不同的订单状态
     */
    String showType;


}
