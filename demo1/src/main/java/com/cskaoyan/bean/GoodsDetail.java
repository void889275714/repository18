package com.cskaoyan.bean;

import lombok.Data;

import java.util.List;

/**
 * @Description 所有商品相关的信息
 * @Date 2019-12-27 17:05
 * @Created by ouyangfan
 */
@Data
public class GoodsDetail {
    Goods goods;
    List<GoodsAttribute> attributes;
    List<GoodsProduct> products;
    List<GoodsSpecification> specifications;
    /**
     * 这个参数list有两个元素
     * 第一个是根据goods_id查询category表得到的id
     * 第二个元素是根据id查category表得到的pid
     */
    List<Integer> categoryIds;
}
