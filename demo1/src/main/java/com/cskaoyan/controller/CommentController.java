package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description TODO
 * @Date 2019-12-30 11:39
 * @Created by ouyangfan
 */
@RestController
@RequestMapping("admin/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    /**
     * 查询所有符合条件的评论信息
     * @param listCondition
     * @return
     */
    @RequestMapping("list")
    public BaseRespVo queryCommentByCondition(ListCondition listCondition){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map map = commentService.queryCommentByListCondition(listCondition);
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 待做：回复评论
     */


    /**
     * 删除评论（假删）
     * @param comment
     * @return
     */
    @RequestMapping("delete")
    public BaseRespVo deleteComment(Comment comment){
        BaseRespVo baseRespVo = new BaseRespVo();
        commentService.deleteComment(comment);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
