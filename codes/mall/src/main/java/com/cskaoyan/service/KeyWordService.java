package com.cskaoyan.service;

import com.cskaoyan.bean.Keyword;

import java.util.List;
import java.util.Map;

public interface KeyWordService {

    Map<String,Object> queryKeyWord(int page,int limit,String keyword,String url,String sort,String order);

    Keyword createKeyWord(Keyword keyword);

    Keyword updateKeyWord(Keyword keyword);

    boolean deleteKeyWord(Keyword keyword);
}
