package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.service.WxSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/search")
public class WxSearchController {

    @Autowired
    WxSearchService wxSearchService;

    /**
     * wx搜索的展示页面
     * @return
     */
    @RequestMapping("index")
    public BaseRespVo indexPage() {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxSearchService.showSearchPage();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 搜索关键字的提示
     */
    @RequestMapping("helper")
    public BaseRespVo showKeyword(String keyword) {
        BaseRespVo baseRespVo = new BaseRespVo();
        List<String> keywords = wxSearchService.hintKeyword(keyword);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(keywords);
        return baseRespVo;
    }

    /**
     * 清除搜索历史记录,没写完，必须得登录得才行
     */
    @RequestMapping("clearhistory")
    public BaseRespVo clearHistory() {
        BaseRespVo baseRespVo = new BaseRespVo();
        wxSearchService.clearHistory();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
