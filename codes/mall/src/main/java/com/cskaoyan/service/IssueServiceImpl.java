package com.cskaoyan.service;

import com.cskaoyan.bean.Issue;
import com.cskaoyan.bean.IssueExample;
import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.mapper.IssueMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssueServiceImpl implements IssueService{

    @Autowired
    IssueMapper issueMapper;

    /**
     * 显示及查询
     * @param page
     * @param limit
     * @param question
     * @param sort
     * @param order
     * @return
     */
    @Override
    public Map<String, Object> queryIssue(int page, int limit, String question, String sort, String order) {
        PageHelper.startPage(page,limit);
        IssueExample issueExample = new IssueExample();
        String issueCondition = sort + " " + order;
        issueExample.setOrderByClause(issueCondition);
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (question != null) {
            criteria.andQuestionLike("%" + question + "%");
        }
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        HashMap<String, Object> map = new HashMap<>();
        long size = issueMapper.countByExample(issueExample);
        map.put("total",(int)size);
        map.put("items",issues);

        return map;
    }


    /**
     * 新增一个issue
     * @param issue
     * @return
     */
    @Override
    public Issue createIssue(Issue issue) {
        issueMapper.insert(issue);
        String question = issue.getQuestion();
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andQuestionLike(question);

        List<Issue> issues = issueMapper.selectByExample(issueExample);
        Integer id = null;
        for (Issue issue1 : issues) {
            id = issue1.getId();
            break;
        }
        Issue issue1 = issueMapper.selectByPrimaryKey(id);
        return issue1;
    }

    /**
     * 修改
     * @param issue
     * @return
     */
    @Override
    public Issue updateIssue(Issue issue) {
        issueMapper.updateByPrimaryKey(issue);
        Integer id = issue.getId();
        Issue issue1 = issueMapper.selectByPrimaryKey(id);
        return issue1;
    }


    /**
     * 通用问题假删除
     * @param issue
     * @return
     */
    @Override
    public boolean deleteIssue(Issue issue) {
        issue.setDeleted(true);
        int update = issueMapper.updateByPrimaryKey(issue);
        if (update > 0) {
            return true;
        }
        return false;
    }


}
