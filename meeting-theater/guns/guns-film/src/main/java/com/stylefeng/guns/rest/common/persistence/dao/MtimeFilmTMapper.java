package com.stylefeng.guns.rest.common.persistence.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.vo.film.filmDetail.FilmDetailVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author ssl
 * @since 2020-01-11
 */
public interface MtimeFilmTMapper extends BaseMapper<MtimeFilmT> {
    FilmDetailVO getFilmDetailByName(@Param("filmName")String filmName);
    FilmDetailVO getFilmDetailById(@Param("uuid")String uuid);

}
