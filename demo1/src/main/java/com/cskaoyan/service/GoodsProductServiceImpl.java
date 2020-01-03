package com.cskaoyan.service;

import com.cskaoyan.bean.GoodsDetail;
import com.cskaoyan.bean.GoodsProduct;
import com.cskaoyan.mapper.GoodsProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Date 2019-12-28 20:43
 * @Created by ouyangfan
 */
@Service
public class GoodsProductServiceImpl implements GoodsProductService{
    @Autowired
    GoodsProductMapper goodsProductMapper;
    /**
     * 更新、插入或者删除goods_product表中信息
     * @param goodsDetail
     */
    @Override
    public void updateGoodsDetailForProduct(GoodsDetail goodsDetail) {
        Integer goodsId = goodsDetail.getGoods().getId();
        List<GoodsProduct> goodsproductList = goodsDetail.getProducts();
        //所有需要更新的和新插入的都不能删，将它们的id记录在下面的list中
        List<Integer> productNotToDeleteIds = new ArrayList<>();
        //第一遍遍历：执行更新与插入操作
        for (GoodsProduct goodsProduct : goodsproductList) {
            //设置更新时间
            goodsProduct.setUpdateTime(new Date());
            if(goodsProduct.getAddTime() == null){

                goodsProduct.setId(null);
                //设置goodsId、add_time、deleted
                goodsProduct.setGoodsId(goodsId);
                goodsProduct.setAddTime(new Date());
                goodsProduct.setDeleted(false);
                //执行插入
                goodsProductMapper.insertSelective(goodsProduct);
                //将要插入的数据的id添加入ids中
                productNotToDeleteIds.add(goodsProduct.getId());
            } else {
                //执行更新
                goodsProductMapper.updateByPrimaryKeySelective(goodsProduct);
                //将更新的id添加到String数组中
                productNotToDeleteIds.add(goodsProduct.getId());
            }
        }
        //将id不在ids中的数据的deleted更改为true
        if(productNotToDeleteIds != null && productNotToDeleteIds.size() > 0){
            String s = productNotToDeleteIds.toString();
            s = "(" + s.substring(1, s.length() - 1) + ")";
            goodsProductMapper.deleteByIdInIds(s, goodsId);
        }
    }
}
