package com.cskaoyan.service;

import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.RegionExample;
import com.cskaoyan.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService{

    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Region> list() {
        RegionExample regionExample = new RegionExample();
        regionExample.createCriteria().andTypeEqualTo((byte)1);
        List<Region> regions = regionMapper.selectByExample(regionExample);
        for (Region region : regions) {
            RegionExample regionExample1 = new RegionExample();
            RegionExample.Criteria criteria = regionExample1.createCriteria();
            criteria.andCodeBetween(region.getCode()*100,region.getCode()*100+99);
            criteria.andTypeEqualTo((byte)2);
            List<Region> regions1 = regionMapper.selectByExample(regionExample1);
            for (Region region1 : regions1) {
                RegionExample regionExample2 = new RegionExample();
                RegionExample.Criteria criteria1 = regionExample2.createCriteria();
                criteria1.andCodeBetween(region1.getCode()*100,region1.getCode()*100+99);
                criteria1.andTypeEqualTo((byte)3);
                List<Region> regions2 = regionMapper.selectByExample(regionExample2);
                region1.setChildren(regions2);
            }
            region.setChildren(regions1);
        }
        return regions;
    }
}
