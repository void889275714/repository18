package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.service.WxBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author bruce
 */
@RestController
@RequestMapping("wx/brand")
public class WxBrandController {

    @Autowired
    WxBrandService wxBrandService;

    /**
     * brand商的详情
     * @return
     */
    @RequestMapping("list")
    public BaseRespVo brandList(int page,int size) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map =  wxBrandService.brandList(page,size);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * brand的详情页面
     */
    @RequestMapping("detail")
    public BaseRespVo brandDetail(int id) {
        BaseRespVo baseRespVo = new BaseRespVo();
        List list = wxBrandService.queryDetail(id);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(list);
        return baseRespVo;
    }

}
