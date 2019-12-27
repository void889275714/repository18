package com.cskaoyan.service;

import com.cskaoyan.bean.Brand;

import java.util.Map;

public interface BrandService {
    Map<String,Object> queryBrand(int page, int limit, String name, int id, String sort, String order);

    Brand createBrand(Brand brand);

    Brand updateBrand(Brand brand);

    boolean deleteBrand(Brand brand);
}
