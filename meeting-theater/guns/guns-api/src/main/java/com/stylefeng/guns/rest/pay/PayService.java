package com.stylefeng.guns.rest.pay;

import com.stylefeng.guns.rest.pay.vo.PayRespVo;

import java.util.Map;

public interface PayService {
    Map<String, String> getPayInfo(String orderId);

    PayRespVo getPayResult(String orderId);

}
