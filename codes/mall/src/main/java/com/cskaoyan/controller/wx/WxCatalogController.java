package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.CateGory;
import com.cskaoyan.service.CateGoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class WxCatalogController {

    @Autowired
    CateGoryService cateGoryService;

    /**
     * 微信 --> 分类 --> 商品显示
     * @param id
     * @return
     */
    @RequestMapping("wx/catalog/current")
    public BaseRespVo showCatalog(int id){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String,Object> cateGories = cateGoryService.wxCateList(id);
        baseRespVo.setData(cateGories);
        return baseRespVo;
    }

}
