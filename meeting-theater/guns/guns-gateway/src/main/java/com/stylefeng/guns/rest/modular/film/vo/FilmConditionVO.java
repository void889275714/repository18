package com.stylefeng.guns.rest.modular.film.vo;


import com.stylefeng.guns.rest.film.vo.film.condition.CatVO;
import com.stylefeng.guns.rest.film.vo.film.condition.SourceVO;
import com.stylefeng.guns.rest.film.vo.film.condition.YearVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmConditionVO implements Serializable {

    private static final long serialVersionUID = 5231308450836025275L;
    private List<CatVO> catInfo;
    private List<SourceVO> sourceInfo;
    private List<YearVO> yearInfo;
}
