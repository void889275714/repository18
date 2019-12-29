package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.GrouponReceive;
import com.cskaoyan.bean.Groupon_rules;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.service.GruoponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

/**
 * 推广管理
 */
@RestController
public class GrouponController {

    @Autowired
    GruoponService gruoponService;

 
    /**
     * @author bruce-g
     * 推广管理的团购活动的显示和查找功能
     */
    @RequestMapping("admin/groupon/listRecord")
    public BaseRespVo groupActivity(ListCondition listCondition) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = gruoponService.queryActivity(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }
}
