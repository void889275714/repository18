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
    public Map<String, Object> queryKeyWord(int page, int limit, String keyword, String url, String sort, String order) {
        KeywordExample keywordExample = new KeywordExample();
        String s = sort + " " + order;
        keywordExample.setOrderByClause(s);
        KeywordExample.Criteria criteria = keywordExample.createCriteria();
        if (keyword != null ){
            criteria.andKeywordLike("%" + keyword + "%");
        }
        if (url != null) {
            criteria.andUrlLike("%" + url + "%");
        }
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        HashMap<String, Object> map = new HashMap<>();
        int size = keywords.size();
        map.put("total",size);
        map.put("items",keywords);
        return map;
    }
}
