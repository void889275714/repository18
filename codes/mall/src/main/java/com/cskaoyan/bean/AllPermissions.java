package com.cskaoyan.bean;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Date 2019-12-31 19:46
 * @Created by ouyangfan
 */
@Data
public class AllPermissions {
    Integer primaryId;
    String id;
    String label;
    Integer pid;
    String api;
    List<AllPermissions> children;
}
