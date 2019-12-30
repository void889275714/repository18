package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2019-12-30 14:56
 * @Created by ouyangfan
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentMapper commentMapper;

    /**
     * 查询符合条件的评论信息
     * @param listCondition
     * @return
     */
    @Override
    public Map queryCommentByListCondition(ListCondition listCondition) {
        //开启分页
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());
        CommentExample commentExample = new CommentExample();
        //获取查询条件
        String userId = listCondition.getUserId();
        String valueId = listCondition.getValueId();
        //添加排序条件
        commentExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        CommentExample.Criteria criteria = commentExample.createCriteria();
        if(userId != null && userId.length() > 0){
            criteria.andUserIdEqualTo(Integer.parseInt(userId));
        }
        if(valueId != null && valueId.length() > 0){
            criteria.andValueIdEqualTo(Integer.parseInt(valueId));
        }
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        int total = (int) commentMapper.countByExample(commentExample);
        Map map = new HashMap();
        map.put("total", total);
        map.put("items", comments);
        return map;
    }

    /**
     * 删除评论
     * 将对应的comment数据的deleted改为true
     * @param comment
     */
    @Override
    public void deleteComment(Comment comment) {
        //更改deleted列和update_time列
        comment.setDeleted(true);
        comment.setUpdateTime(new Date());
        commentMapper.updateByPrimaryKeySelective(comment);
    }

    /**
     * 待做：回复评论
     */


}
