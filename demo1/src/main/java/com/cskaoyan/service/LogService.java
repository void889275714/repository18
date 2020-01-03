package com.cskaoyan.service;

import com.cskaoyan.bean.ListLogCondition;

import java.util.Map;

public interface LogService {

    Map<String, Object> showLogList(ListLogCondition listLogCondition);
}
