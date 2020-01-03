package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.service.wx.WxFootPrintService;
import com.cskaoyan.service.wx.WxFootPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author bruce
 */
@RestController
@RequestMapping("wx/footprint")
public class WxFootPrintController {

    @Autowired
    WxFootPrintService wxFootPrintService;

    /**
     * 显示用户足迹
     */
    @RequestMapping("list")
    public BaseRespVo footList(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = wxFootPrintService.showList(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }


}
