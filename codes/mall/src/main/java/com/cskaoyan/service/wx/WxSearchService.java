package com.cskaoyan.service;

import java.util.List;
import java.util.Map;

public interface WxSearchService {

    Map showSearchPage();

    List<String> hintKeyword(String keyword);

    void clearHistory();

}
