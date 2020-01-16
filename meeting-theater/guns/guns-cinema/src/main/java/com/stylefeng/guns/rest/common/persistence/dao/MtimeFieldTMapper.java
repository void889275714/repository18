package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.cinema.vo.HallInfo;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author pined
 * @since 2020-01-08
 */
public interface MtimeFieldTMapper extends BaseMapper<MtimeFieldT> {
    HallInfo getHallInfo(@Param("fieldId") String fieldId);
}
