package com.stylefeng.guns.rest.modular.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.alipay.AliPayService;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.pay.PayService;
import com.stylefeng.guns.rest.pay.vo.OrderPayCinemaVo;
import com.stylefeng.guns.rest.pay.vo.PayRespVo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Service(interfaceClass = PayService.class)
public class PayServiceImpl implements PayService {

    @Reference(interfaceClass = AliPayService.class)
    private AliPayService aliPayService;

    @Reference(interfaceClass = OrderService.class)
    private OrderService orderService;

    @Override
    public Map<String, String> getPayInfo(String orderId) {
        OrderPayCinemaVo payCinemaVo = orderService.queryCinemaDetailByOrderId(orderId);
        String payQRCode = aliPayService.getPayQRCode(orderId, payCinemaVo.getCinemaName(), payCinemaVo.getPrice(), String.valueOf(payCinemaVo.getCinemaId()));

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("QRCodeAddress",payQRCode);
        hashMap.put("orderId",orderId);
        return hashMap;
    }

    @Override
    public PayRespVo getPayResult(String orderId) {
        PayRespVo respVo = new PayRespVo();
        Integer payResult = aliPayService.getPayResult(orderId);
        respVo.setOrderId(orderId);
        respVo.setOrderStatus(payResult);
        if (payResult==1){
            respVo.setOrderMsg("支付成功");
        }else {
            respVo.setOrderMsg("支付失败");
        }

        return respVo;
    }
}
