package com.cskaoyan.mapper;

import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GoodsExample;
import java.util.List;

import com.cskaoyan.bean.WxGrouponMsg;
import com.cskaoyan.bean.WxNeedMsg;

import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {
    long countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    WxGrouponMsg selectByGoodId(Integer goodsId);

    WxNeedMsg AfterSelectByGoodId(Integer Id);
}