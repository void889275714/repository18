package com.cskaoyan.service;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.BrandExample;
import com.cskaoyan.mapper.BrandMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxBrandServiceImpl implements WxBrandService{

    @Autowired
    BrandMapper brandMapper;

    /**
     * 展示品牌制造商
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map brandList(int page, int size) {
        // 通过得到的两个参数进行分页
        PageHelper.startPage(page,size);

        // 得到逆向工程的bean
        BrandExample brandExample = new BrandExample();

        //查询得到brand的List
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        //计算brand的总数
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        long total = pageInfo.getTotal();

        //放入map中
        HashMap<String, Object> map = new HashMap<>();
        map.put("brandList",brands);
        map.put("totalPages",total);
        return map;
    }

    /**
     * 展示品牌制造商的详情
     * @param id
     * @return
     */
    @Override
    public List queryDetail(int id) {
        // 得到逆向工程的bean
        BrandExample brandExample = new BrandExample();
        //判断查询条件
        brandExample.createCriteria().andIdEqualTo(id);
        //查询得到brand的List
        List<Brand> brands = brandMapper.selectByExample(brandExample);

        return brands;
    }
}
