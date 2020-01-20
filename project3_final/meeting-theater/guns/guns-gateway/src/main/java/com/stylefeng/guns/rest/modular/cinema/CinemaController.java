package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.ResponseVO;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.BaseRespVo;
import com.stylefeng.guns.rest.cinema.vo.CinemaBrandAreaHall;
import com.stylefeng.guns.rest.cinema.vo.CinemasRequest;
import com.stylefeng.guns.rest.cinema.vo.FieldInfoVO;
import com.stylefeng.guns.rest.modular.cache.LocalCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@Slf4j
@RestController
public class CinemaController {

    @Reference(interfaceClass = CinemaService.class,check = false)
    private CinemaService cinemaService;

    @Autowired
    private LocalCacheService localCacheService;

    @Autowired
    RedisTemplate redisTemplate;

    private static final String LOCAL_CACHE_KEY_CINEMA_INDEX = "cinemaIndex";

    private static final String REDIS_CACHE_KEY_CINEMA_INDEX = "cinemaRedis";


    /**
     * 4、获取场次详细信息接口
     * @param cinemaId
     * @param fieldId
     * @return
     */
    @RequestMapping("cinema/getFieldInfo")
    public ResponseVO fieldInfo(String cinemaId,String fieldId) {

        //二级缓存
        //首先查本地缓存
        //如果本地缓存中有，直接返回
        //如果本地缓存中没有，就去查询 redis中的缓存
        //如果 redis中没有，则查数据库中的缓存，查到之后放到redis中和本地缓存中
        Object o = localCacheService.get(LOCAL_CACHE_KEY_CINEMA_INDEX);
        if(o != null){
            ResponseVO responseVO = new ResponseVO();
            responseVO.setData(o);
            responseVO.setMsg("成功");
            responseVO.setStatus(0);
            return responseVO;
        } else {
            log.info("本地缓存中没有相关数据");
            FieldInfoVO fieldInfo1 = (FieldInfoVO) redisTemplate.opsForValue().get(REDIS_CACHE_KEY_CINEMA_INDEX);
           if (fieldInfo1 != null){
               log.info("从redis中取到数据");
               localCacheService.set(LOCAL_CACHE_KEY_CINEMA_INDEX,fieldInfo1);
               ResponseVO responseVO = new ResponseVO();
               responseVO.setData(fieldInfo1);
               responseVO.setMsg("成功");
               responseVO.setStatus(0);
               return responseVO;
           }
        }

        ResponseVO responseVO = new ResponseVO();
        FieldInfoVO fieldInfo = cinemaService.getFieldInfo(cinemaId, fieldId);
        //如果是第一次的话，就存一下吧
        localCacheService.set(LOCAL_CACHE_KEY_CINEMA_INDEX,fieldInfo);
        redisTemplate.opsForValue().set(REDIS_CACHE_KEY_CINEMA_INDEX,fieldInfo);
        System.out.println("初始化本地缓存及redis缓存");

        responseVO.setData(fieldInfo);
        responseVO.setImgPre("http://img.meetingshop.cn/");
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
        responseVO.setImgPre("http://img.meetingshop.cn/");
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
        responseVO.setImgPre("http://localhost:8082");
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
