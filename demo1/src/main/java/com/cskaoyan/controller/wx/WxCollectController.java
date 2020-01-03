package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Collect;
import com.cskaoyan.service.wx.WxCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WxCollectController {


    @Autowired
    WxCollectService wxCollectService;

    /**
     *
     * 用户收藏
     *
     * 请求体:
     * {"type":0,"valueId":1110017}
     * 响应体:
     * {"errno":0,"data":{"type":"add"},"errmsg":"成功"}
     *
     * {"errno":0,"data":{"type":"delete"},"errmsg":"成功"}
     */
    @RequestMapping("wx/collect/addordelete")
    public BaseRespVo addOrDelete(@RequestBody Collect collect){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        String addordelete = wxCollectService.addordelete(collect.getType(), collect.getValueId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("type",addordelete);
        baseRespVo.setData(map);
        return baseRespVo;
    }


    /**
     * 微信前台 -->  个人 -->  商品收藏
     * @param type
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("wx/collect/list")
    public BaseRespVo showCollect(byte type,int page,int size){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String, Object> map = wxCollectService.showCollectList(type, page, size);
        baseRespVo.setData(map);
        return baseRespVo;
    }

}
