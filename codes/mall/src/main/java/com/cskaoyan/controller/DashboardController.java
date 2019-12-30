package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description TODO
 * @Date 2019-12-28 15:04
 * @Created by ouyangfan
 */
@RestController
public class DashboardController {

    @Autowired
    DashboardService dashboardService;
    /**
     * 查询商品数量，用户数量，产品数量，订单数量
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"goodsTotal": 250,
     * 		"userTotal": 22,
     * 		"productTotal": 255,
     * 		"orderTotal": 10
     *        },
     * 	"errmsg": "成功"
     * }
     * @return
     */
    @RequestMapping("admin/dashboard")
    public BaseRespVo dashboard(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String, Integer> map = dashboardService.queryDashboardInfo();
        baseRespVo.setData(map);
        return baseRespVo;
    }
}
