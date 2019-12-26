package com.cskaoyan.service;

import com.cskaoyan.bean.User;

import java.util.List;

public interface UserService {
    List<User> queryUsers(int pageNumber, int size);
}
