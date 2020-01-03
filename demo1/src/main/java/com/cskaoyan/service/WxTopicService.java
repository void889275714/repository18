package com.cskaoyan.service;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.topic;

import java.util.List;
import java.util.Map;

public interface WxTopicService {
    /**
     * 专题
     * @param listCondition
     * @return
     */
    //专题显示
    Map<Object,Object> wxSelectTopic(ListCondition listCondition);
    /**
     * 专题详情
     * @param toPic
     * @return
     */
    //专题详情
    Map<Object,Object> wxTopicDetail(topic toPic);
    //专题相关/推荐
    List<topic> wxTopicRelated(topic toPic);
    /**
     * 专题评论列表
     */
    //显示评论数量
    Map<Object,Object> wxCommentCount(ListCondition listCondition);
    //显示评论列表
    Map wxUserComment(ListCondition listCondition);
    /**
     * 添加评论
     */
    //添加评论
    Comment wxCommentPost(Comment comment);
}
