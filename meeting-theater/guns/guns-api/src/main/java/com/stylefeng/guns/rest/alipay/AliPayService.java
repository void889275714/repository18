package com.stylefeng.guns.rest.alipay;

public interface AliPayService {

    String getPayQRCode(String orderId,String cinemaName,String price,String cinemaId);

    Integer getPayResult(String orderId);
}
