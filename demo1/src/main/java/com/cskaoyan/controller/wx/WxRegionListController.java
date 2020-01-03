package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.Region;
import com.cskaoyan.service.WxRegionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WxRegionListController {

    @Autowired
    WxRegionListService wxRegionListService;

    /**
     * 功能：
     * 显示城市
     *
     * 请求体：
     * pid: 0
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": [{
     * 		"id": 1,
     * 		"pid": 0,
     * 		"name": "北京市",
     * 		"type": 1,
     * 		"code": 11
     *              }],
     * 	"errmsg": "成功"
     * }
     */
    @RequestMapping("wx/region/list")
    public BaseRespVo wxRegionList(ListCondition listCondition) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        List<Region> data = wxRegionListService.selectRegionLis(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(data);
        return baseRespVo;

    }
}
