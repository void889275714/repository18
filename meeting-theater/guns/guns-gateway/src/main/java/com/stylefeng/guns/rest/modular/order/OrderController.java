package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.ResponseVO;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.vo.OrderBuyTickets;
import com.stylefeng.guns.rest.user.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Reference(interfaceClass = OrderService.class)
    private OrderService orderService;

    @Reference(interfaceClass = UserService.class)
    private UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 下单之后的页面
     * 需要带上请求头的内容 @RequestHeader String Authorization
     * @return
     */
    @RequestMapping("order/buyTickets")
    public ResponseVO orderBuyTickets(String fieldId,String soldSeats,String seatsName,@RequestHeader String Authorization){
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        ResponseVO responseVO = new ResponseVO();
        Boolean trueSeats = orderService.isTrueSeats(fieldId, soldSeats);
        if (trueSeats) {
            //true  已经出售   false 未出售
            Boolean soldSeats1 = orderService.isSoldSeats(fieldId, soldSeats);
            Integer userId = userService.queryUserId(username);

            if (!soldSeats1){
                OrderBuyTickets orderBuyTickets = orderService.orderBuyTicketInfo(fieldId, soldSeats, seatsName,userId);
                responseVO.setMsg("");
                responseVO.setImgPre("");
                responseVO.setStatus(0);
                responseVO.setNowPage("");
                responseVO.setTotalPage("");
                responseVO.setData(orderBuyTickets);
                return responseVO;
            }
        }
        responseVO.setStatus(999);
        responseVO.setMsg("系统出现异常，请联系管理员");
        return responseVO;
    }


    /**
     * 接口2  user --> 显示订单信息
     *
     * @return
     */
    @RequestMapping("order/getOrderInfo")
    public ResponseVO getOrderInfo(String nowPage, String pageSize,@RequestHeader String Authorization){
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        Integer userId = userService.queryUserId(username);


        ResponseVO responseVO = new ResponseVO();
        List<OrderBuyTickets> data = orderService.showOrderInfo(userId);
        responseVO.setMsg("未支付");
        responseVO.setTotalPage("");
        responseVO.setData(data);
        responseVO.setImgPre("");
        responseVO.setStatus(0);
        responseVO.setNowPage(nowPage);
        return responseVO;
    }

}
