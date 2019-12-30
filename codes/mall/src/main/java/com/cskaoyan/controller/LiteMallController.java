package com.cskaoyan.controller;


import com.cskaoyan.bean.FreightMsg;
import com.cskaoyan.bean.MallMsg;
import com.cskaoyan.bean.OrderMsg;
import com.cskaoyan.bean.WxMsg;

import com.cskaoyan.bean.configmall.BaseRespVo;
import com.cskaoyan.service.LiteMallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("admin/config")
public class LiteMallController {
    @Autowired
    LiteMallService liteMallService;

    /**
     * 显示商场配置
     */
@GetMapping("/mall")
    public BaseRespVo queryMallMsg(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
            Map map = liteMallService.queryMallMsg();
            baseRespVo.setErrno(0);
            baseRespVo.setData(map);
            baseRespVo.setErrmsg("成功");
            return baseRespVo;

        }
        @PostMapping("/mall")
    public BaseRespVo updateMallMsg(@RequestBody MallMsg mallMsg){
            BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
            if(mallMsg!=null){
                boolean flag = liteMallService.updateMallMsg(mallMsg);
                if(flag==true){
                    baseRespVo.setErrno(0);
                    baseRespVo.setErrmsg("成功");
                    return baseRespVo;
                }

            }
            baseRespVo.setErrno(10000);
            baseRespVo.setErrmsg("失败");
            return baseRespVo;
        }
    @GetMapping("/express")
    /**
     * 显示运费
     */
    public  BaseRespVo queryExpressMsg(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        Map map = liteMallService.queryExpressMsg();

        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }



    @PostMapping("/express")
    /**
     * 更改运费配置
     */
    public BaseRespVo updateFreightMsg(@RequestBody FreightMsg freightMsg){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        if(freightMsg!=null){
            boolean flag = liteMallService.updateFreightMsg(freightMsg);
            if(flag==true){
                baseRespVo.setErrno(0);
                baseRespVo.setErrmsg("成功");
                return baseRespVo;
            }
        }
        baseRespVo.setErrno(10000);
        baseRespVo.setErrmsg("失败");
        return baseRespVo;
    }

    /**
     * 获取设置订单信息
     * @return
     */
    @GetMapping("/order")
    public BaseRespVo queryOrderMsg(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
         Map map = liteMallService.queryOrderMsg();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }


    @PostMapping("/order")
    /**
     * 更改运费信息
     */
    public BaseRespVo updateOrderMsg(@RequestBody OrderMsg orderMsg){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        if(orderMsg!=null){
            boolean flag = liteMallService.updateOrderMsg(orderMsg);
            if(flag==true){
                baseRespVo.setErrno(0);
                baseRespVo.setErrmsg("成功");
                return baseRespVo;
            }
        }
        baseRespVo.setErrno(10000);
        baseRespVo.setErrmsg("失败");
        return baseRespVo;
    }

    @GetMapping("/wx")
    /**
     * 显示微信小程序配置信息
     */
    public BaseRespVo queryWxMsg(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map map = liteMallService.queryWxMsg();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }


    @PostMapping("/wx")
/**
 * 更改微信小程序配置信息
 */
    public BaseRespVo updateWxMsg(@RequestBody WxMsg wxMsg){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        if(wxMsg!=null){
            boolean flag = liteMallService.updateWxMsg(wxMsg);
            if(flag==true){
                baseRespVo.setErrno(0);
                baseRespVo.setErrmsg("成功");
                return baseRespVo;
            }
        }
        baseRespVo.setErrno(10000);
        baseRespVo.setErrmsg("失败");
        return baseRespVo;
    }
    }






