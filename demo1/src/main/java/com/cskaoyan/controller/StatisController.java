package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.service.StatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatisController {

    @Autowired
    StatisService statisService;

    /**
     * 功能：
     * 统计报表-订单统计
     *
     * 请求体：
     * 无
     *
     * 响应体：
     *
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"columns": ["day", "orders", "customers", "amount", "pcr"],
     * 		"rows": [{
     * 			"amount": 939.00,
     * 			"orders": 2,
     * 			"customers": 1,
     * 			"day": "2019-07-08",
     * 			"pcr": 939.00
     *                }]* 	},
     * 	"errmsg": "成功"
     * }
     */

    @RequestMapping("admin/stat/order")
    public BaseRespVo selectStatisOrder() {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<Object,Object> map = statisService.selectStatisOrder();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("查询成功");
        baseRespVo.setData(map);
        return baseRespVo;

    }
}
