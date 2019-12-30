package com.cskaoyan.service;

import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GoodsDetail;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.exception.ItemAlreadyExistException;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2019-12-26 22:20
 * @Created by ouyangfan
 */
public interface GoodsService {

    Map queryAllGoods(ListCondition listCondition);

    GoodsDetail queryGoodsDetail(int id);

    Map<String, Object> queryCatAndBrand();

    void updateGoodsDetail(GoodsDetail goodsDetail);

    void createGoods(GoodsDetail goodsDetail) throws ItemAlreadyExistException;

    void deleteGoods(Goods goods);
}
