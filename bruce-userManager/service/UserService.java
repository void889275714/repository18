package com.cskaoyan.service;


import com.cskaoyan.bean.Paging;

import java.util.Map;

public interface UserService {
    Map queryUsers(Paging paging);

}
