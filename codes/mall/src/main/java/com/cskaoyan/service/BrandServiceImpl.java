package com.cskaoyan.service;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.BrandExample;
import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.mapper.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService{

    @Autowired
    BrandMapper brandMapper;


    /**
     * 商场管理 --> 品牌制造商 --> 显示
     * @param page
     * @param limit
     * @param name
     * @param id
     * @param sort
     * @param order
     * @return
     */
    @Override
    public Map<String, Object> queryBrand(int page, int limit, String name, int id, String sort, String order) {
        BrandExample brandExample = new BrandExample();
        String brandCondition = sort + " " + order;
        brandExample.setOrderByClause(brandCondition);
        BrandExample.Criteria criteria = brandExample.createCriteria();
        if (name != null ){
            criteria.andNameLike("%" + name + "%");
        }
        if (id > 0 ) {
            criteria.andIdEqualTo(id);
        }
        List<Brand> brands = brandMapper.selectByExample(brandExample);

        HashMap<String, Object> map = new HashMap<>();
        int size = brands.size();
        map.put("total",size);
        map.put("items",brands);
        return map;
    }

    @Override
    public Brand createBrand(Brand brand) {
        brandMapper.insert(brand);
        String picUrl = brand.getPicUrl();

        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andPicUrlEqualTo(picUrl);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        Integer id = null;
        for (Brand brand1 : brands) {
            id = brand1.getId();
        }
        Brand brand1 = brandMapper.selectByPrimaryKey(id);
        return brand1;
    }

    @Override
    public Brand updateBrand(Brand Brand) {
        return null;
    }

    @Override
    public boolean deleteBrand(Brand Brand) {
        return false;
    }
}
