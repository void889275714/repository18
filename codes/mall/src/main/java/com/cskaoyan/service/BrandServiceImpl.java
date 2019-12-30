package com.cskaoyan.service;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.BrandExample;
import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.mapper.BrandMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService{

    @Autowired
    BrandMapper brandMapper;


    /**
     * 商场管理 --> 品牌制造商 --> 显示(查询)
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
        PageHelper.startPage(page,limit);

        BrandExample brandExample = new BrandExample();
        String brandCondition = sort + " " + order;
        brandExample.setOrderByClause(brandCondition);
        BrandExample.Criteria criteria = brandExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null ){
            criteria.andNameLike("%" + name + "%");
        }
        if (id > 0 ) {
            criteria.andIdEqualTo(id);
        }
        List<Brand> brands = brandMapper.selectByExample(brandExample);

        HashMap<String, Object> map = new HashMap<>();
        long size = brandMapper.countByExample(brandExample);
        map.put("total",(int)size);
        map.put("items",brands);
        return map;
    }

    /**
     * 新增
     * @param brand
     * @return
     */
    @Override
    public Brand createBrand(Brand brand) {
        brandMapper.insert(brand);

        //获取刚插入的Id
        String picUrl = brand.getPicUrl();
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andPicUrlEqualTo(picUrl);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        Integer id = null;
        for (Brand brand1 : brands) {
            id = brand1.getId();
            break;
        }


        Brand brand1 = brandMapper.selectByPrimaryKey(id);
        return brand1;
    }


    /**
     * 更新品牌
     * @param brand
     * @return
     */
    @Override
    public Brand updateBrand(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
        Integer id = brand.getId();
        Brand brand1 = brandMapper.selectByPrimaryKey(id);
        return brand1;
    }


    /**
     * 假删
     * @param brand
     * @return
     */
    @Override
    public boolean deleteBrand(Brand brand) {
        brand.setDeleted(true);
        int update = brandMapper.updateByPrimaryKey(brand);
        if (update > 0) {
            return true;
        }
        return false;
    }
}
