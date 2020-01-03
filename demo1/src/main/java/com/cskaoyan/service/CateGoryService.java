package com.cskaoyan.service;

import com.cskaoyan.bean.CateGory;

import java.util.List;
import java.util.Map;

public interface CateGoryService {
    List<CateGory> cateList();

    List<Map<String,Object>> cateL1List();

    CateGory createCategory(CateGory cateGory);

    CateGory updateCateGory(CateGory cateGory);

    boolean deleteCateGory(CateGory cateGory);

    Map<String,Object> wxCateList(int id);

    Map<String,Object> wxCateIndexList();
}
