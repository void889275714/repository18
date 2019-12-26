package com.cskaoyan.service;

import com.cskaoyan.bean.Keyword;
import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.mapper.KeywordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeyWordServiceImpl implements KeyWordService{

    @Autowired
    KeywordMapper keywordMapper;


    @Override
    public Map<String, Object> queryKeyWord(int page, int limit, String keyword, String url) {
        KeywordExample keywordExample = new KeywordExample();
        KeywordExample.Criteria criteria = keywordExample.createCriteria();
        if (keyword != null || url!=null) {
            criteria.andKeywordLike("%" + keyword + "%").andUrlLike("%" + url + "%");
        }
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        HashMap<String, Object> map = new HashMap<>();
        int size = keywords.size();
        map.put("total",size);
        map.put("items",keywords);
        return map;
    }
}
