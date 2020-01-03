package com.cskaoyan.service;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.CommentExample;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.UserComment;
import com.cskaoyan.bean.topic;
import com.cskaoyan.bean.topicExample;
import com.cskaoyan.mapper.CommentMapper;
import com.cskaoyan.mapper.topicMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxTopicServiceImpl implements WxTopicService {

    @Autowired
    topicMapper topicmapper;

    @Autowired
    CommentMapper commentMapper;

    /**
     * 微信专题显示
     * @param listCondition
     * @return
     */
    @Override
    public Map<Object, Object> wxSelectTopic(ListCondition listCondition) {
        //开启分页
        PageHelper.startPage(listCondition.getPage(),listCondition.getSize());
        //添加排序条件
        topicExample topicexample = new topicExample();
        topicExample.Criteria criteria = topicexample.createCriteria();
        criteria.andDeletedEqualTo(false);
        //拿到dao/mapper层返回的数据
        List<topic> topicLists = topicmapper.selectByExample(topicexample);
        long count = topicmapper.countByExample(topicexample);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data",topicLists);
        map.put("count",count);
        return map;

    }

    @Override
    public Map<Object, Object> wxTopicDetail(topic toPic) {

        topic toPic1 = topicmapper.selectByPrimaryKey(toPic.getId());
        String[] goods = toPic1.getGoods();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("topic",toPic1);
        map.put("goods",goods);
        return map;

    }

    @Override
    public List<topic> wxTopicRelated(topic toPic) {

        topicExample topicexample = new topicExample();
        topicExample.Criteria criteria = topicexample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andIdEqualTo(toPic.getId());
        List<topic> topicLists = topicmapper.selectByExample(topicexample);
        return topicLists;

    }

    @Override
    public Map<Object, Object> wxCommentCount(ListCondition listCondition) {

        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andValueIdEqualTo(Integer.valueOf(listCondition.getValueId()));
        criteria.andTypeEqualTo((byte) listCondition.getType());
        long count = commentMapper.countByExample(commentExample);
        Comment comment = commentMapper.selectByPrimaryKey(Integer.valueOf(listCondition.getValueId()));
        Boolean hasPicture1 = comment.getHasPicture();
        Integer hasPicture;
        if (hasPicture1) {
            hasPicture = 1;
        } else {
            hasPicture = 0;
        }
        HashMap<Object,Object> map = new HashMap<>();
        map.put("hasPicCount",hasPicture);
        map.put("allCount",count);
        return map;

    }

    @Override
    public Map wxUserComment(ListCondition listCondition) {
        PageHelper.startPage(listCondition.getPage(), listCondition.getSize());
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andValueIdEqualTo(Integer.valueOf(listCondition.getValueId()));
        criteria.andTypeEqualTo((byte) listCondition.getType());
        Integer valueId = Integer.valueOf(listCondition.getValueId());
        int type = listCondition.getType();
        List<UserComment> data = commentMapper.wxSelectUserComment(valueId, type);
        long count = commentMapper.countByExample(commentExample);
        Integer currentPage = 0;
        if (count > 0 && count <= 20) {
            currentPage = 1;
        } else if (count > 20 && count <= 40) {
            currentPage = 2;
        } else if (count > 40 && count <= 60) {
            currentPage = 3;
        } else if (count > 60 && count <= 80) {
            currentPage = 4;
        } else {
            currentPage = 5;
        }
        Map map = new HashMap();
        map.put("data", data);
        map.put("count", count);
        map.put("currentPage", currentPage);
        return map;

    }

    @Override
    public Comment wxCommentPost(Comment comment) {

        int insert = commentMapper.insertSelective(comment);
        if (insert == 1) {
            Comment comment1 = commentMapper.selectByPrimaryKey(comment.getId());
            return comment1;
        }
        return comment;
    }

}
