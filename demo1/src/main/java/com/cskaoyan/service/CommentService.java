package com.cskaoyan.service;


import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.ListCondition;

import java.util.Map;

/**
 * @Description TODO
 * @Date 2019-12-30 14:56
 * @Created by ouyangfan
 */
public interface CommentService {
    Map queryCommentByListCondition(ListCondition listCondition);

    void deleteComment(Comment comment);
}
