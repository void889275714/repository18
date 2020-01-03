package com.cskaoyan.bean;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Date 2019-12-28 16:37
 * @Created by ouyangfan
 */
@Data
public class CateGoryForReturnBean {
    String value;
    String label;
    List<CateGoryForReturnBean> children;
}
