package com.cskaoyan.service.wx;

import com.cskaoyan.bean.*;

import com.cskaoyan.mapper.KeywordMapper;
import com.cskaoyan.mapper.Search_historyMapper;
import com.cskaoyan.service.wx.WxSearchService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxSearchServiceImpl implements WxSearchService {

    /**
     * 进入搜索页面会需要查询两条数据，一个展示在搜索栏上的默认字段，另一个展示在下面的热门搜索
     */
    @Autowired
    KeywordMapper keywordMapper;

    @Autowired
    Search_historyMapper searchHistoryMapper;

    /**
     * 展示搜索的界面
     * @return
     */
    @Override
    public Map showSearchPage() {
        // 查询keyword表中default为true的数据，放入马map中
        HashMap hashMap = new HashMap();

        KeywordExample keywordExample = new KeywordExample();
        keywordExample.createCriteria().andIsDefaultEqualTo(true);
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        // 而且只能展示一条默认数据
        Keyword keyword = keywords.get(0);
        // 查询is_hot字段为true的值
        keywordExample.createCriteria().andIsHotEqualTo(true);
        List<Keyword> hotkeywords = keywordMapper.selectByExample(keywordExample);


        if (SecurityUtils.getSubject().getPrincipal()!= null) {
            User user = (User)SecurityUtils.getSubject().getPrincipal() ;
            Search_historyExample search_historyExample = new Search_historyExample();
            search_historyExample.createCriteria().andUserIdEqualTo(user.getId());
            List<Search_history> histories = searchHistoryMapper.selectByExample(search_historyExample);
            hashMap.put("historyKeywordList",histories);
        }
        //放入map中

        hashMap.put("defaultKeyword",keyword);
        hashMap.put("hotKeywordList",hotkeywords);
        return hashMap;
    }

    /**
     * 自动提示一个字符串数组的keyword
     * @param keyword
     * @return
     */
    @Override
    public List hintKeyword(String keyword) {
        // 得到逆向工程的bean对象
        KeywordExample keywordExample = new KeywordExample();
        // 查询条件是keyword
        keywordExample.createCriteria().andKeywordLike("%" + keyword + "%");
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        // 查询出来的是对象，只需得到keyword字段
        ArrayList arrayList = new ArrayList();
        for (Keyword keyword1 : keywords) {
            String keyword2 = keyword1.getKeyword();
            arrayList.add(keyword2);
        }
        //然后返回，所以用一个循环来接收得到的字段
        return arrayList;
    }

    /**
     * 清楚用户的历史记录
     */
    @Override
    public void clearHistory() {
        Search_historyExample search_historyExample = new Search_historyExample();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer id = user.getId();
        search_historyExample.createCriteria().andUserIdEqualTo(id);
        searchHistoryMapper.deleteByExample(search_historyExample);
    }
}
