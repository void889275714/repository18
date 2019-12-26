package com.cskaoyan.service;

import com.cskaoyan.bean.Keyword;

import java.util.List;
import java.util.Map;

public interface KeyWordService {

    Map<String,Object> queryKeyWord(int page,int limit,String keyword,String url);
}
