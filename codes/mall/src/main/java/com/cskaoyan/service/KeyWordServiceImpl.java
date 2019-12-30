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


    /**
     * 查询以及显示keyword
     * @param page
     * @param limit
     * @param keyword
     * @param url
     * @param sort
     * @param order
     * @return
     */
    @Override
    public Map<String, Object> queryKeyWord(int page, int limit, String keyword, String url,String sort, String order) {
        KeywordExample keywordExample = new KeywordExample();
        String s = sort + " " + order;
        keywordExample.setOrderByClause(s);
        KeywordExample.Criteria criteria = keywordExample.createCriteria();
        criteria.andDeletedEqualTo(false);
       /* 假删的话。。
        criteria.andDeletedIsNotNull();*/

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


    /**
     * 增加keyword
     * @param keyword
     * @return
     */
    @Override
    public Keyword createKeyWord(Keyword keyword) {
        keywordMapper.insert(keyword);
        String keyword1 = keyword.getKeyword();
        KeywordExample keywordExample = new KeywordExample();
        keywordExample.createCriteria().andKeywordLike(keyword1);
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        Integer id = null;
        for (Keyword keyword2 : keywords) {
            id = keyword2.getId();
            break;
        }
        Keyword keyword2 = keywordMapper.selectByPrimaryKey(id);
        return keyword2;
    }

    /**
     * 修改
     * @param keyword
     * @return
     */
    @Override
    public Keyword updateKeyWord(Keyword keyword) {
        keywordMapper.updateByPrimaryKey(keyword);
        Integer id = keyword.getId();
        Keyword keyword2 = keywordMapper.selectByPrimaryKey(id);
        return keyword2;
    }


    /**
     * 假删除关键词
     * @param keyword
     * @return
     */
    @Override
    public boolean deleteKeyWord(Keyword keyword) {
        keyword.setDeleted(true);
        int update = keywordMapper.updateByPrimaryKey(keyword);
        if (update > 0) {
            return true;
        }
        return false;
    }


}
