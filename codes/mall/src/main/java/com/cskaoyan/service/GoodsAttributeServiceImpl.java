package com.cskaoyan.service;

import com.cskaoyan.bean.GoodsAttribute;
import com.cskaoyan.bean.GoodsAttributeExample;
import com.cskaoyan.bean.GoodsDetail;
import com.cskaoyan.mapper.GoodsAttributeMapper;
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
public class GoodsAttributeServiceImpl implements GoodsAttributeService{
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;
    /**
     * 更新、插入或者删除goods_attribute表中信息
     * @param goodsDetail
     */
    @Override
    public void updateGoodsDetailForAttribute(GoodsDetail goodsDetail) {
        Integer goodsId = goodsDetail.getGoods().getId();
        List<GoodsAttribute> goodsAttributeList = goodsDetail.getAttributes();
        //所有需要更新的和新插入的都不能删，将它们的id记录在下面的list中
        List<Integer> attributeNotToDeleteIds = new ArrayList<>();
        //第一遍遍历：执行更新与插入操作
        for (GoodsAttribute goodsAttribute : goodsAttributeList) {
            //设置更新时间
            goodsAttribute.setUpdateTime(new Date());
            if(goodsAttribute.getAddTime() == null){
                //设置goodsId、add_time、deleted
                goodsAttribute.setGoodsId(goodsId);
                goodsAttribute.setAddTime(new Date());
                goodsAttribute.setDeleted(false);
                //执行插入
                goodsAttributeMapper.insertSelective(goodsAttribute);
                //将刚插入的数据的id添加入ids中
                attributeNotToDeleteIds.add(goodsAttribute.getId());
            } else {
                //执行更新
                goodsAttributeMapper.updateByPrimaryKeySelective(goodsAttribute);
                //将更新的id添加到String数组中
                attributeNotToDeleteIds.add(goodsAttribute.getId());
            }
        }
        //将id不在ids中的数据的deleted更改为true
        if(attributeNotToDeleteIds != null && attributeNotToDeleteIds.size() > 0){
            String s = attributeNotToDeleteIds.toString();
            s = "(" + s.substring(1, s.length() - 1) + ")";
            goodsAttributeMapper.deleteByIdInIds(s, goodsId);
        }
    }
}
