package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.film.service.FilmAsynServiceAPI;
import com.stylefeng.guns.film.service.FilmServiceAPI;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmRequestVO;
import com.stylefeng.guns.vo.film.filmDetail.*;
import com.stylefeng.guns.vo.film.banner.*;
import com.stylefeng.guns.vo.film.condition.CatVO;
import com.stylefeng.guns.vo.film.condition.SourceVO;
import com.stylefeng.guns.vo.film.condition.YearVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/film")
@Slf4j
public class FilmController {

    private static final String IMG_PRE = "http://img.meetingshop.cn/";

    @Reference(interfaceClass = FilmServiceAPI.class)
    private FilmServiceAPI filmServiceAPI;

    //dubbo异步调用,添加async = true，springboot需要去gateWayApplication手动添加注解开启@EnableAsync
    @Reference(interfaceClass = FilmAsynServiceAPI.class,async = true)
    private FilmAsynServiceAPI filmAsynServiceAPI;


    @RequestMapping(value = "/getIndex", method = RequestMethod.GET)
    public ResponseVO getIndex() {

        ResponseVO responseVo = null;
        List<BannerVO> bannerVo = filmServiceAPI.getBannerVo();
        FilmVO hotFilmVo = filmServiceAPI.getHotFilmVo(true, 6, 1, 1, 99, 99, 99);
        FilmVO soonFilmsVo = filmServiceAPI.getSoonFilmsVo(true, 6, 1, 1, 99, 99, 99);
        List<BoxRankingVO> boxRankingVo = filmServiceAPI.getBoxRankingVo(10);
        List<ExpectRankingVO> expectRankingVo = filmServiceAPI.getExpectRankingVo(10);
        List<Top100VO> top100Vo = filmServiceAPI.getTop100Vo(6);

        FilmIndexVO data = new FilmIndexVO();
        data.setBanners(bannerVo);
        data.setHotFilms(hotFilmVo);
        data.setSoonFilms(soonFilmsVo);
        data.setBoxRanking(boxRankingVo);
        data.setExpectRanking(expectRankingVo);
        data.setTop100(top100Vo);

        try {
            responseVo = ResponseVO.success(IMG_PRE, data);
            //业务异常
            if (responseVo == null) {
                return ResponseVO.serviceFail("查询失败，无banner可加载");
            } else {
                //业务正常返回
                return responseVo;
            }

        } catch (Exception e) {
            log.info("系统出现异常，请联系管理员,e{}", e);
            e.printStackTrace();
            //系统异常
            ResponseVO.serviceFail("系统出现异常，请联系管理员");
            return responseVo;
        }
    }

    @RequestMapping(value = "/getConditionList", method = RequestMethod.GET)
    public ResponseVO getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId) {

        FilmConditionVO filmConditionVO = new FilmConditionVO();

        //catID:类型集合
        Boolean flag = false;//判断是否全部都是false
        List<CatVO> cats = filmServiceAPI.getCats();//数据库查出cats
        List<CatVO> catResult = new ArrayList<>();//循环判断后，真正存的catResult
        CatVO cat = null;
        //类型集合，判断集合是否存在catId，如果存在就将存在的激活为Active;如果不存在，默认全部为Active状态
        for (CatVO catVO : cats) {
            //遇到99就跳出此次循环，防止遇到99又重新循环到99，所以利用外层的新cat标记catVO
            //比如用户输出catId=6,数据库只用1,2,3,99,4
            if (catVO.getCatId().equals("99")) {
                cat = catVO;
                continue;
            }
            if (catVO.getCatId().equals(catId)) {
                flag = true;//=true就说明就存在实体激活为Active
                catVO.setActive(true);
            } else {
                catVO.setActive(false);
            }
            catResult.add(catVO);

        }
        //如果数据库中都没有查出，就说明flag = false，就全部默认设置为Active状态
        if (!flag) {
            cat.setActive(true);
            catResult.add(cat);
        } else {
            cat.setActive(false);
            catResult.add(cat);
        }

        //sourceId
        flag = false;//flag先设置为false
        List<SourceVO> sources = filmServiceAPI.getSources();//数据库查出cats
        List<SourceVO> sourcesResult = new ArrayList<>();//循环判断后，真正存的catResult
        SourceVO source = null;
        for (SourceVO sVO : sources) {
            if (sVO.getSourceId().equals("99")) {
                source = sVO;
            }
            if (sVO.getSourceId().equals(sourceId)) {
                flag = true;//=true就说明就存在实体激活为Active
                sVO.setIsActive(true);
            } else {
                sVO.setIsActive(false);
            }
            sourcesResult.add(sVO);

        }
        if (!flag) {
            source.setIsActive(true);
            sourcesResult.add(source);
        } else {
            source.setIsActive(false);
            sourcesResult.add(source);
        }

        //yearId
        flag = false;
        List<YearVO> years = filmServiceAPI.getYears();
        List<YearVO> yearsResult = new ArrayList<>();
        YearVO year = null;
        for (YearVO yVO : years) {
            if (yVO.getYearId().equals("99")) {
                year = yVO;
                continue;
            }
            if (yVO.getYearId().equals(yearId)) {
                flag = true;
                yVO.setIsActive(true);
            } else {
                yVO.setIsActive(false);
            }
            yearsResult.add(yVO);

        }
        if (!flag) {
            year.setIsActive(true);
            yearsResult.add(year);
        } else {
            year.setIsActive(false);
            yearsResult.add(year);
        }

        filmConditionVO.setCatInfo(catResult);
        filmConditionVO.setSourceInfo(sourcesResult);
        filmConditionVO.setYearInfo(yearsResult);

        return ResponseVO.success(filmConditionVO);
    }

    @RequestMapping(value = "/getFilms", method = RequestMethod.GET)
    public ResponseVO getFilms(FilmRequestVO filmrequestVO) {
        String imgPre = "http://img.meetingshop.cn";
        FilmVO filmVO = null;
        switch (filmrequestVO.getShowType()) {
            case 1:
                filmVO = filmServiceAPI.getHotFilmVo(
                        false, filmrequestVO.getPageSize(), filmrequestVO.getNowPage(),
                        filmrequestVO.getSortId(), filmrequestVO.getSourceId(), filmrequestVO.getYearId(),
                        filmrequestVO.getCatId());
                break;
            case 2:
                filmVO = filmServiceAPI.getSoonFilmsVo(
                        false, filmrequestVO.getPageSize(), filmrequestVO.getNowPage(),
                        filmrequestVO.getSortId(), filmrequestVO.getSourceId(), filmrequestVO.getYearId(),
                        filmrequestVO.getCatId());
                break;
            case 3:
                filmVO = filmServiceAPI.getClassicFilms(
                        filmrequestVO.getPageSize(), filmrequestVO.getNowPage(),
                        filmrequestVO.getSortId(), filmrequestVO.getSourceId(), filmrequestVO.getYearId(),
                        filmrequestVO.getCatId());
                break;
            default:
                filmVO = filmServiceAPI.getHotFilmVo(
                        false, filmrequestVO.getPageSize(), filmrequestVO.getNowPage(),
                        filmrequestVO.getSortId(), filmrequestVO.getSourceId(), filmrequestVO.getYearId(),
                        filmrequestVO.getCatId());
                break;
        }
        return ResponseVO.success(filmVO.getNowPage(), filmVO.getTotalPage(),
                imgPre, filmVO.getFilmInfo());
    }

    @RequestMapping(value = "/films/{searchParam}", method = RequestMethod.GET)
    public ResponseVO films(@PathVariable("searchParam") String searchParam,int searchType) throws ExecutionException, InterruptedException {
        //根据searchType 判断查询类型
        FilmDetailVO filmDetails = filmServiceAPI.getFilmDetails(searchType, searchParam);
        //查询失败返回
        if(filmDetails ==null){
            return ResponseVO.serviceFail("查询失败，无影片可加载”");
        }else if(filmDetails.getFilmId() ==null ||  filmDetails.getFilmId().trim().length() ==0){
            return  ResponseVO.serviceFail("查询失败，无影片可加载”");
        }

        //不同类型查询，传入的条件会略有不同
        String filmId = filmDetails.getFilmId();

        //使用异步调用
        filmAsynServiceAPI.getFilmDesc(filmId);//获取影片描述信息
        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();

        filmAsynServiceAPI.getImgs(filmId);//图片信息
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();


        filmAsynServiceAPI.getDectInfo(filmId);//导演信息
        Future<ActorVO> actorVOFuture = RpcContext.getContext().getFuture();

        filmAsynServiceAPI.getActors(filmId);//演员信息
        Future<List<ActorVO>> actorsVOFuture = RpcContext.getContext().getFuture();

        //整合info信息
        InfoRequestVO infoRequestVO = new InfoRequestVO();
        ActorRequestVO actorRequestVO = new ActorRequestVO();

        //组织actors信息
        actorRequestVO.setActors(actorsVOFuture.get());//抛出异常
        actorRequestVO.setDirector(actorVOFuture.get());
        //组织info信息
        infoRequestVO.setActors(actorRequestVO);
        infoRequestVO.setBiography(filmDescVOFuture.get().getBiography());
        infoRequestVO.setFilmId(filmId);
        infoRequestVO.setImgVO(imgVOFuture.get());

        //组织返回值
        filmDetails.setInfo04(infoRequestVO);
        //查询影片的详细信息，学习dubbo的异步查询

        return ResponseVO.success("http://img.meetingshop.cn/",filmDetails);
    }
}
