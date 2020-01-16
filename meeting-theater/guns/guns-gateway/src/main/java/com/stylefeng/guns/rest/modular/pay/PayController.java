package com.stylefeng.guns.rest.modular.pay;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.model.Result;
import com.stylefeng.guns.rest.pay.PayService;
import com.stylefeng.guns.rest.pay.vo.PayRespVo;
import io.netty.util.internal.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PayController {

    @Reference(interfaceClass = PayService.class, retries = 1)
    private PayService payService;


    @RequestMapping("order/getPayInfo")
    public Result getPayInfo(String orderId){
        Map<String, String> map = payService.getPayInfo(orderId);
        if (map == null || map.size() == 0) {
            return Result.failure();
        }
        if (StringUtil.isNullOrEmpty(map.get("QRCodeAddress"))) {
            return Result.statusIsOne("订单支付失败，请重试！");
        }
        return Result.ok(map,"http://localhost:8080/");
    }


    @RequestMapping("order/getPayResult")
    public Result getPayResult(String orderId,Integer tryNums){
        if (tryNums>3) return Result.statusIsOne("订单支付失败，请稍后重试!");
        PayRespVo respVo = payService.getPayResult(orderId);
        if (respVo==null) return Result.failure();
        return Result.ok(respVo);
    }

}
