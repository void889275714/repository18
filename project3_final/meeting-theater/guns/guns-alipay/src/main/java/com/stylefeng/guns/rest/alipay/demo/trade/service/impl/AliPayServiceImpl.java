package com.stylefeng.guns.rest.alipay.demo.trade.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.stylefeng.guns.rest.alipay.AliPayService;
import com.stylefeng.guns.rest.alipay.demo.trade.Main;
import org.springframework.stereotype.Component;


@Component
@Service(interfaceClass = AliPayService.class)
public class AliPayServiceImpl implements AliPayService {

    private Main main = new Main();


    @Override
    public String getPayQRCode(String orderId, String cinemaName, String price, String cinemaId) {
        String qrCodePath = main.getPayQRCode(orderId, cinemaName, price, cinemaId);
        return qrCodePath;
    }

    /**
     * 查看订单状态
     * @param orderId
     * @return
     */
    @Override
    public Integer getPayResult(String orderId){
        //String payResult = main.queryPayResult(orderId);
        String payResult = "TRADE_SUCCESS";
        Integer integer = null;
        if ("TRADE_SUCCESS".equals(payResult)){
            integer = 1;
        }else if ("TRADE_CLOSED".equals(payResult)||"TRADE_FINISHED".equals(payResult)){
            integer = -1;
        }else {
            integer = 0;
        }
        return integer;
    }
}
