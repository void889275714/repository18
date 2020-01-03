package com.cskaoyan.service;

import com.cskaoyan.bean.Issue;

import java.util.Map;

public interface IssueService {
    Map<String,Object> queryIssue(int page, int limit, String question, String sort, String order);

    Issue createIssue(Issue issue);

    Issue updateIssue(Issue issue);

    boolean deleteIssue(Issue issue);
}
