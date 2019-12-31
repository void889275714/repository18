package com.cskaoyan.service;

import java.util.List;
import java.util.Map;

public interface WxBrandService {
    Map brandList(int page, int size);

    List queryDetail(int id);
}
