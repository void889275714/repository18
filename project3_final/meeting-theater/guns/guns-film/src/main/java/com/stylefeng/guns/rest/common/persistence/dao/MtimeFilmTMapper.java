package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.cinema.vo.FilmInfo;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.film.vo.film.filmDetail.FilmDetailVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author pined
 * @since 2020-01-08
 */
public interface MtimeFilmTMapper extends BaseMapper<MtimeFilmT> {
    FilmDetailVO getFilmDetailByName(@Param("filmName")String filmName);
    FilmDetailVO getFilmDetailById(@Param("uuid")String uuid);
    FilmInfo getFilmInfoById(@Param("id") Integer uuid);
}
