package com.cskaoyan.service;

import com.cskaoyan.bean.GoodsDetail;
import com.cskaoyan.bean.GoodsSpecification;
import com.cskaoyan.mapper.GoodsSpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Date 2019-12-28 20:44
 * @Created by ouyangfan
 */
@Service
public class GoodsSpecificationServiceImpl implements GoodsSpecificationService{
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;
    /**
     * 更新、插入或者删除goods_specification表中信息
     * @param goodsDetail
     */
    @Override
    public void updateGoodsDetailForSpecification(GoodsDetail goodsDetail) {
        Integer goodsId = goodsDetail.getGoods().getId();
        List<GoodsSpecification> goodsSpecificationList = goodsDetail.getSpecifications();
        //所有需要更新的和新插入的都不能删，将它们的id记录在下面的list中
        List<Integer> SpecificationNotToDeleteIds = new ArrayList<>();
        //第一遍遍历：执行更新与插入操作
        for (GoodsSpecification goodsSpecification : goodsSpecificationList) {
            //设置更新时间
            goodsSpecification.setUpdateTime(new Date());
            if(goodsSpecification.getId() == null){
                //设置goodsId、add_time、deleted
                goodsSpecification.setGoodsId(goodsId);
                goodsSpecification.setAddTime(new Date());
                goodsSpecification.setDeleted(false);
                //执行插入
                goodsSpecificationMapper.insertSelective(goodsSpecification);
                //将刚插入的数据的id添加入ids中
                SpecificationNotToDeleteIds.add(goodsSpecification.getId());
            } else {
                //执行更新
                goodsSpecificationMapper.updateByPrimaryKeySelective(goodsSpecification);
                //将更新的id添加到String数组中
                SpecificationNotToDeleteIds.add(goodsSpecification.getId());
            }
        }
        //将id不在ids中的数据的deleted更改为true
        if(SpecificationNotToDeleteIds != null && SpecificationNotToDeleteIds.size() > 0){
            String s = SpecificationNotToDeleteIds.toString();
            s = "(" + s.substring(1, s.length() - 1) + ")";
            goodsSpecificationMapper.deleteByIdInIds(s, goodsId);
        }
    }
}
