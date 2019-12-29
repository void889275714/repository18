package com.cskaoyan.mapper;

import com.cskaoyan.bean.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SelectOrderMapper {

    List<Order> queryOrderByMyself(@Param("userId") String userId, @Param("orderSn") String orderSn, @Param("orderStatusArray") List orderStatusArray);
}
