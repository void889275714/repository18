package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListWxCondition;

import com.cskaoyan.service.wx.WxGrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("wx/groupon")
public class GrouponWxController {

    @Autowired
    WxGrouponService wxGrouponService;

    /**
     * 显示团购信息
     * @param listWxCondition
     * @return
     */
    @GetMapping("/list")
    public BaseRespVo queryGrouponMsg(ListWxCondition listWxCondition){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map map = wxGrouponService.queryWxGrouponMsg(listWxCondition);

        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @GetMapping("/my")
    public BaseRespVo queryMyGrouponMsg(ListWxCondition listWxCondition){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map map = wxGrouponService.queryMyGrouponMsg(listWxCondition);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        baseRespVo.setData(map);
        return baseRespVo;
    }


}
