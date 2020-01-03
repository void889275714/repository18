package com.cskaoyan.service.wx;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.GrouponMapper;
import com.cskaoyan.mapper.OrderMapper;
import com.cskaoyan.mapper.Order_goodsMapper;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Description TODO
 * @Date 2020-01-01 18:42
 * @Created by ouyangfan
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class WxOrderServiceImpl implements WxOrderService{
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GrouponMapper grouponMapper;
    @Autowired
    Order_goodsMapper orderGoodsMapper;
    @Autowired
    WxCartService wxCartService;
    /**
     * 根据showType查询不同状态的订单信息
     * 0：全部，1：待付款，2：待发货，3：待收货，4：待评价
     * 请求参数：showType、page、size
     * @param listCondition
     * @return
     */
    @Override
    public Map queryOrderList(ListCondition listCondition) {
        //开启分页
        int size = listCondition.getSize();
        PageHelper.startPage(listCondition.getPage(), size);
        //获取showType
        String showType1 = listCondition.getShowType();
        Integer showType = 0;
        if(!"".equals(showType1)){
            showType = Integer.parseInt(showType1);
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //取出user中的id
        Integer userId = user.getId();
        //根据此userId从order表中查询所有该用户的订单信息
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUserIdEqualTo(userId).andDeletedEqualTo(false);
        // 根据showType的值0：全部，1：待付款，2：待发货，3：待收货，4：待评价
        // 添加不同的条件
        if(showType == 1){
            //待付款对应order_status为101
            criteria.andOrderStatusEqualTo((short) 101);
        } else if(showType == 2){
            //待发货对应order_status为201
            criteria.andOrderStatusEqualTo((short) 201);
        } else if(showType == 3){
            //待收货对应order_status为301
            criteria.andOrderStatusEqualTo((short) 301);
        } else if(showType == 4){
            //待评价对应order_status为401,402
            criteria.andOrderStatusGreaterThan((short) 400);
        }
        //得到满足条件的订单
        List<Order> orders = orderMapper.selectByExample(orderExample);
        //获取count，计算totalPages
        int count = (int) orderMapper.countByExample(orderExample);
        int totalPages;
        if(count % size == 0){
            //刚好整除
            totalPages = count / size;
        } else {
            //不能整除
            totalPages = count / size + 1;
        }
        //返回的bean的list
        List<OrderDetailForReturn> data = new ArrayList<>();
        //遍历订单list
        for (Order order : orders) {
            OrderDetailForReturn orderDetailForReturn = new OrderDetailForReturn();
            //获取order的订单状态
            Short orderStatus = order.getOrderStatus();
            //根据订单状态设置orderStatusText和handleOption
            Map<String, Object> map = orderStatus(orderStatus);
            String orderStatusText = (String) map.get("orderStatusText");
            HandleOption handleOption = (HandleOption) map.get("handleOption");
            orderDetailForReturn.setOrderStatusText(orderStatusText);
            orderDetailForReturn.setHandleOption(handleOption);
            //根据order_id查询groupon表看是否参与团购
            GrouponExample example = new GrouponExample();
            Integer orderId = order.getId();
            example.createCriteria().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
            long isGroup = grouponMapper.countByExample(example);
            //设置isGroupin字段
            orderDetailForReturn.setGroupin(isGroup == 1L);
            //设置id、orderSn、actualPrice
            orderDetailForReturn.setId(orderId);
            orderDetailForReturn.setOrderSn(order.getOrderSn());
            orderDetailForReturn.setActualPrice(order.getActualPrice());
            //查询order_goods表获取goodsList
            Order_goodsExample orderGoodsExample = new Order_goodsExample();
            orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
            List<Order_goods> goodsList = orderGoodsMapper.selectByExample(orderGoodsExample);
            orderDetailForReturn.setGoodsList(goodsList);
            //将orderDetailForReturn添加到list中
            data.add(orderDetailForReturn);
        }
        Map map = new HashMap();
        map.put("data", data);
        map.put("count", count);
        map.put("totalPages", totalPages);
        return map;
    }

    /**
     * 从order表和order_goods表中获取订单详情
     * @param orderId
     * @return
     */
    @Override
    public Map queryOrderDetatilByOrderId(Integer orderId) {
        //获取order表信息
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andIdEqualTo(orderId).andDeletedEqualTo(false);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        Order orderInfo = orders.get(0);
        //设置orderInfo中的orderStatusText和handleOption
        Map<String, Object> map = orderStatus(orderInfo.getOrderStatus());
        String orderStatusText = (String) map.get("orderStatusText");
        HandleOption handleOption = (HandleOption) map.get("handleOption");
        orderInfo.setOrderStatusText(orderStatusText);
        orderInfo.setHandleOption(handleOption);
        //获取order_goods表中信息
        Order_goodsExample example = new Order_goodsExample();
        List<Order_goods> orderGoods = orderGoodsMapper.selectByExample(example);
        Map map1 = new HashMap();
        map1.put("orderInfo", orderInfo);
        map1.put("orderGoods", orderGoods);
        return map1;
    }

    /**
     * 改变订单状态
     * @param orderId
     * @param changeOrderStatusTo
     */
    @Override
    public void changeOrderStatus(Integer orderId, Short changeOrderStatusTo) {
        Order record = new Order();
        record.setId(orderId);
        //更改状态码
        record.setOrderStatus(changeOrderStatusTo);
        //设置更新时间
        record.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 订单删除
     * 将此orderId的order表和order_goods表中的数据deleted值修改
     * @param orderId
     */
    @Override
    public void orderDelete(Integer orderId) {
        //更新order表
        Order record = new Order();
        record.setId(orderId);
        record.setUpdateTime(new Date());
        record.setDeleted(true);
        orderMapper.updateByPrimaryKeySelective(record);
        //更新order_goods表
        Order_goods orderGoods = new Order_goods();
        orderGoods.setOrderId(orderId);
        orderGoods.setUpdateTime(new Date());
        orderGoods.setDeleted(true);
        orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
    }

    /**
     * 付款
     * 修改状态码，pay_time
     * @param orderId
     */
    @Override
    public void orderPrepay(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date());
        order.setPayTime(new Date());
        //修改状态码
        order.setOrderStatus((short) 201);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 确认收货
     * @param orderId
     */
    @Override
    public void orderConfirm(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date());
        order.setConfirmTime(new Date());
        //修改状态码
        order.setOrderStatus((short) 401);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 提交订单
     * @param orderSubmit
     * @return
     */
    @Override
    public Map orderSubmit(OrderSubmit orderSubmit) {
        //取出数据，调用cartService中的checkout方法
        Integer cartId = orderSubmit.getCartId();
        Integer addressId = orderSubmit.getAddressId();
        Integer couponId = orderSubmit.getCouponId();
        Integer grouponRulesId = orderSubmit.getGrouponRulesId();
        CartCheckOutBean cartCheckOutBean = new CartCheckOutBean();
        cartCheckOutBean.setCartId(cartId);
        cartCheckOutBean.setAddressId(addressId);
        cartCheckOutBean.setCouponId(couponId);
        cartCheckOutBean.setGrouponRulesId(grouponRulesId);
        CartCheckOutForReturn cartCheckOutForReturn = wxCartService.cartCheckOut(cartCheckOutBean);
        String message = orderSubmit.getMessage();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //将数据存入Order对象中
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderSn(new Date().toString() + user.getUsername());
        order.setOrderStatus((short) 101);
        Address address = cartCheckOutForReturn.getCheckedAddress();
        order.setConsignee(address.getName());
        order.setMobile(address.getMobile());
        order.setAddress(address.getAddress());
        order.setMessage(message);
        order.setGoodsPrice(cartCheckOutForReturn.getGoodsTotalPrice());
        order.setFreightPrice(cartCheckOutForReturn.getFreightPrice());
        order.setCouponPrice(cartCheckOutForReturn.getCouponPrice());
        order.setGrouponPrice(cartCheckOutForReturn.getGrouponPrice());
        order.setOrderPrice(cartCheckOutForReturn.getOrderTotalPrice());
        order.setActualPrice(cartCheckOutForReturn.getActualPrice());
        order.setAddTime(new Date());
        order.setUpdateTime(new Date());
        order.setDeleted(false);
        order.setIntegralPrice(BigDecimal.valueOf(0));
        orderMapper.insertSelective(order);
        Map map = new HashMap();
        map.put("orderId", order.getId());
        return map;
    }

    /**
     * 根据订单状态设置orderStatusText和handleOption
     * 101 ："未付款"
     * 102 ："用户取消"
     * 103 ："系统取消"
     * 201 ："已付款"
     * 202 ："申请退款"
     * 203 ："已退款"
     * 301 ："已发货"
     * 401 ："用户收货"
     * 402 ："系统收货"
     * @param orderStatus 订单状态
     */
    private Map<String, Object> orderStatus(Short orderStatus) {
        HandleOption handleOption = new HandleOption();
        String orderStatusText = null;
        switch (orderStatus){
            case 101:
                orderStatusText = "未付款";
                handleOption.setCancel(true);
                handleOption.setPay(true);
                break;
            case 102:
                orderStatusText = "用户取消";
                handleOption.setDelete(true);
                handleOption.setRebuy(true);
                break;
            case 103:
                orderStatusText = "系统取消";
                handleOption.setDelete(true);
                break;
            case 201:
                orderStatusText = "已付款";
                handleOption.setRefund(true);
                break;
            case 202:
                orderStatusText = "申请退款";
                handleOption.setCancel(true);
                break;
            case 203:
                orderStatusText = "已退款";
                handleOption.setDelete(true);
                break;
            case 301:
                orderStatusText = "已发货";
                handleOption.setConfirm(true);
                handleOption.setRefund(true);
                break;
            case 401:
                orderStatusText = "用户收货";
                handleOption.setComment(true);
                handleOption.setRebuy(true);
                break;
            case 402:
                orderStatusText = "系统收货";
                handleOption.setComment(true);
                handleOption.setRebuy(true);
                break;
            default:
                throw new IllegalArgumentException();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("orderStatusText", orderStatusText);
        map.put("handleOption", handleOption);
        return map;
    }
}
