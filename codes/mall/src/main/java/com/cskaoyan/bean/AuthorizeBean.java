package com.cskaoyan.bean;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Date 2019-12-31 21:40
 * @Created by ouyangfan
 */
@Data
public class AuthorizeBean {
    String roleId;
    List<String> permissions;
}
