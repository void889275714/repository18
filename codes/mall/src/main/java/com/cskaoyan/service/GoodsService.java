package com.cskaoyan.service;

import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GoodsDetail;
import com.cskaoyan.bean.ListConditon;

import java.util.List;

/**
 * @Description TODO
 * @Date 2019-12-26 22:20
 * @Created by ouyangfan
 */
public interface GoodsService {

    List<Goods> queryAllGoods(ListConditon listConditon);

    Long countGoods();

    GoodsDetail queryGoodsDetail(int id);
}
