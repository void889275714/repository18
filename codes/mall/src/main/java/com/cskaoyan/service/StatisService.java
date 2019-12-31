package com.cskaoyan.service;

import com.cskaoyan.bean.StatisOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface StatisService {

    Map<Object,Object> selectStatisOrder();

}
