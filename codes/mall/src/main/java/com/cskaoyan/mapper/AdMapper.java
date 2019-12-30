package com.cskaoyan.mapper.promote;

import com.cskaoyan.bean.promote.Ad;
import com.cskaoyan.bean.promote.AdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdMapper {

    long countByExample(AdExample example);

    int deleteByExample(AdExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ad record);

    int insertSelective(Ad record);

    List<Ad> selectByExample(AdExample example);

    Ad selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ad record, @Param("example") AdExample example);

    int updateByExample(@Param("record") Ad record, @Param("example") AdExample example);

    int updateByPrimaryKeySelective(Ad record);

    int updateByPrimaryKey(Ad record);
}