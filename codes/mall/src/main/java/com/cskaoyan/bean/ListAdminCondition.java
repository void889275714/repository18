package com.cskaoyan.bean;

import lombok.Data;


/**
 * page=1&limit=20&username=dd&sort=add_time&order=desc
 */
@Data
public class ListAdminCondition {
    int page;
    int limit;
    String username;
    String sort;
    String order;
}
