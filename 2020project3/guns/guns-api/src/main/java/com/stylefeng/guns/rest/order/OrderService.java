package com.stylefeng.guns.rest.order;

import com.stylefeng.guns.rest.order.vo.OrderBuyTickets;

import java.util.List;

public interface OrderService {

    /**
     * 第一个接口  /order/buyTickets 的子接口
     * @param fieldId
     * @param seatId
     * @return
     */
    public Boolean isTrueSeats (String fieldId,String seatId);


    /**
     * 第一个接口的子接口
     * 判断是否已经售卖
     *
     */
    public Boolean isSoldSeats (String fieldId,String seatId);


    /**
     * 点在线选座后的页面信息
     * @param fieldId
     * @param soldSeats
     * @param seatsName
     * @return
     */
    public OrderBuyTickets orderBuyTicketInfo(String fieldId,String soldSeats,String seatsName,Integer userId);

    /**
     * 你看这碗又大又圆，装了整个订单信息
     */
    public List<OrderBuyTickets> showOrderInfo(Integer userId);
}
