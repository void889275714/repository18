package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.ResponseVO;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.BaseRespVo;
import com.stylefeng.guns.rest.cinema.vo.CinemaBrandAreaHall;
import com.stylefeng.guns.rest.cinema.vo.CinemasRequest;
import com.stylefeng.guns.rest.cinema.vo.FieldInfoVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CinemaController {

    @Reference(interfaceClass = CinemaService.class,check = false)
    private CinemaService cinemaService;

    /**
     * 4、获取场次详细信息接口
     * @param cinemaId
     * @param fieldId
     * @return
     */
    @RequestMapping("cinema/getFieldInfo")
    public ResponseVO fieldInfo(String cinemaId,String fieldId) {
        ResponseVO responseVO = new ResponseVO();
        FieldInfoVO fieldInfo = cinemaService.getFieldInfo(cinemaId, fieldId);
        responseVO.setData(fieldInfo);
        responseVO.setImgPre("");
        responseVO.setMsg("成功");
        responseVO.setStatus(0);
        responseVO.setNowPage("");
        responseVO.setTotalPage("");
        return responseVO;
    }


    /**
     * 2、获取影院列表查询条件
     */
    @RequestMapping("cinema/getCondition")
    public ResponseVO getCinemaCondition(Integer brandId,String hallType,Integer areaId) {
        //默认brandId  hallType  areaId 都是99
        ResponseVO responseVO = new ResponseVO();
        CinemaBrandAreaHall CBAH = cinemaService.getCinemaCondition(brandId,hallType,areaId);
        responseVO.setData(CBAH);
        responseVO.setImgPre("");
        responseVO.setMsg("");
        responseVO.setStatus(0);
        responseVO.setNowPage("");
        responseVO.setTotalPage("");
        return responseVO;

    }

    /**
     * 3、获取播放场次接口
     * @param cinemaId
     * @return
     */
    @RequestMapping("cinema/getFields")
    public ResponseVO getCinemaFields(String cinemaId){
        ResponseVO responseVO = new ResponseVO();
        Map<String,Object> data = cinemaService.getCinemaField(cinemaId);
        responseVO.setData(data);
        responseVO.setImgPre("");
        responseVO.setMsg("");
        responseVO.setStatus(0);
        responseVO.setNowPage("");
        responseVO.setTotalPage("");
        return responseVO;
    }


    /**
     * 1、展示影院页面的接口
     * @return
     */
    @RequestMapping("cinema/getCinemas")
    public BaseRespVo cinemaListVo(CinemasRequest cinemasRequest) {
        BaseRespVo baseRespVo = cinemaService.getCinemasInfos(cinemasRequest);
        return baseRespVo;
    }

}
