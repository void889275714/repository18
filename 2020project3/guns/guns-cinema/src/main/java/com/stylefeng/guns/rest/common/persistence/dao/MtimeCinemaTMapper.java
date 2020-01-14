package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.cinema.vo.CinemaMsg;
import com.stylefeng.guns.rest.cinema.vo.CinemasRequest;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCinemaT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影院信息表 Mapper 接口
 * </p>
 *
 * @author pined
 * @since 2020-01-08
 */
public interface MtimeCinemaTMapper extends BaseMapper<MtimeCinemaT> {

    List<CinemaMsg> getCinemasInfos( @Param("CinemasRequest") CinemasRequest cinemasRequest);
}
