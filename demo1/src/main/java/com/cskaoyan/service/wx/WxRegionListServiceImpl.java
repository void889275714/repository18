package com.cskaoyan.service;

import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.RegionExample;
import com.cskaoyan.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxRegionListServiceImpl implements WxRegionListService {

    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Region> selectRegionLis(ListCondition listCondition) {

        RegionExample regionExample = new RegionExample();
        RegionExample.Criteria criteria = regionExample.createCriteria();
        criteria.andPidEqualTo(listCondition.getPid());
        List<Region> regionList = regionMapper.selectByExample(regionExample);
        return regionList;

    }
}
