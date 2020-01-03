package com.cskaoyan.service;

import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.Region;

import java.util.List;

public interface WxRegionListService {
    /**
     * 获取区域列表
     * @param listCondition
     * @return
     */
    List<Region> selectRegionLis(ListCondition listCondition);

}
