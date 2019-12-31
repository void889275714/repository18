package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.service.WxHomeIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WxHomeIndexController {

    @Autowired
    WxHomeIndexService wxHomeIndexService;
    /**
     * wx首页的index
     * @return
     */
    @RequestMapping("wx/home/index")
    public BaseRespVo homeIndex() {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxHomeIndexService.queryIndex();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

}
