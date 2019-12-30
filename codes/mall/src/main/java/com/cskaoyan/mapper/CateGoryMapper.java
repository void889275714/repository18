package com.cskaoyan.mapper;

import com.cskaoyan.bean.CateGory;
import com.cskaoyan.bean.CateGoryExample;
import java.util.List;

import com.cskaoyan.bean.CateGoryForReturnBean;
import org.apache.ibatis.annotations.Param;

public interface CateGoryMapper {
    long countByExample(CateGoryExample example);

    int deleteByExample(CateGoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CateGory record);

    int insertSelective(CateGory record);

    List<CateGory> selectByExample(CateGoryExample example);

    CateGory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CateGory record, @Param("example") CateGoryExample example);

    int updateByExample(@Param("record") CateGory record, @Param("example") CateGoryExample example);

    int updateByPrimaryKeySelective(CateGory record);

    int updateByPrimaryKey(CateGory record);

    List<CateGoryForReturnBean> selectFatherAndSonCategory();
}
