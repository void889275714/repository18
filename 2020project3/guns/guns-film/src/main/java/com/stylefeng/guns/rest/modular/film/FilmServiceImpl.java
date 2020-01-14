package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.cinema.vo.FilmInfo;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.film.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {

    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;


    @Override
    public FilmInfo getFilmInfoByFieldId(String fieldId) {
        Integer id = Integer.valueOf(fieldId);
        FilmInfo filmInfoById = mtimeFilmTMapper.getFilmInfoById(id);
        return filmInfoById;
    }
}
