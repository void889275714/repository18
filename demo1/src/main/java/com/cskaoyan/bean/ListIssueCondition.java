package com.cskaoyan.bean;

import lombok.Data;

@Data
public class ListIssueCondition {
    int page;
    int limit;
    String sort;
    String order;
    String question;

}
