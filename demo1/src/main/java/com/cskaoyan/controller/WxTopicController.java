package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.topic;
import com.cskaoyan.service.WxTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class WxTopicController {

    @Autowired
    WxTopicService wxTopicService;

    /**
     * 专题
     */

    /**
     * 功能：
     * 显示专题内容
     *
     * 请求体：
     * page: 1
     * size: 10
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"data": [{
     * 			"id": 330,
     * 			"title": "12",
     * 			"subtitle": "12",
     * 			"price": 12.00,
     * 			"readCount": "12",
     * 			"picUrl": "http://192.168.2.100:8081/wx/storage/123.png"
     *                    }],
     * 		"count": 23
     * 	},
     * 	"errmsg": "成功"
     * }
     */
    @RequestMapping("wx/topic/list")
    public BaseRespVo wxSelectTopic(ListCondition listCondition) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<Object, Object> map = wxTopicService.wxSelectTopic(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("查询成功");
        baseRespVo.setData(map);
        return baseRespVo;

    }

    /**
     * 专题详情
     */

    /**
     * 功能:
     * 显示专题详情
     *
     * 请求体：
     * id：281
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"topic": {
     * 			"id": 281,
     * 			"title": "条纹新风尚",
     * 			"subtitle": "经典百搭，时尚线条",
     * 			"price": 29.00,
     * 			"readCount": "76.5k",
     * 			"picUrl": "https://yanxuan.nosdn.127.net/0826.jpg",
     * 			"sortOrder": 0,
     * 			"goods": [],
     * 			"addTime": "2018-01-31 19:00:00",
     * 			"updateTime": "2018-01-31 19:00:00",
     * 			"deleted": false,
     * 			"content": "<img src=\"//yanxuan.nosdn.127.net/75c55a.jpg\">"
     *                },
     * 		"goods": []* 	},
     * 	"errmsg": "成功"
     * }
     */
    @RequestMapping("wx/topic/detail")
    public BaseRespVo  wxTopicDetail(topic toPic) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<Object, Object> map = wxTopicService.wxTopicDetail(toPic);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("查询成功");
        baseRespVo.setData(map);
        return baseRespVo;

    }

    /**
     * 功能：
     * 专题相关/推荐
     *
     * 请求体：
     * id: 281
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": [专题数据，专题数据],
     * 	"errmsg": "成功"
     * }
     */
    @RequestMapping("wx/topic/related")
    public BaseRespVo wxTopicRealated(topic toPic) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        List<topic> topicLists = wxTopicService.wxTopicRelated(toPic);
        if (topicLists == null) {
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("没有相关专题");
        } else {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("查询成功");
            baseRespVo.setData(topicLists);
        }
        return baseRespVo;

    }

    /**
     * 专题评论列表
     */

    /**
     * 功能：
     * 显示评论数量
     *
     * 请求体：
     * valueId: 314
     * type: 1
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"hasPicCount": 1,
     * 		"allCount": 8
     *        },
     * 	"errmsg": "成功"
     * }
     */
    @RequestMapping("wx/comment/count")
    public BaseRespVo wxCommentCount(ListCondition listCondition) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<Object,Object> map = wxTopicService.wxCommentCount(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("查询成功");
        baseRespVo.setData(map);
        return baseRespVo;

    }

    /**
     * 功能：
     * 显示评论列表
     *
     * 请求体：
     * valueId: 314
     * type: 1
     * size: 20
     * page: 1
     * showType: 0
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"data": [{
     * 			"userInfo": {
     * 				"nickName": "dr lan",
     * 				"avatarUrl": ""
     *                        },
     * 			"addTime": "2019-12-31 08:06:15",
     * 			"picList": ["http://192.168.2.100:8081/wx.jpg"],
     * 			"content": "321"}],
     * 		"count": 8,
     * 		"currentPage": 1
     *    },
     * 	"errmsg": "成功"
     * }
     */
    @RequestMapping("wx/comment/list")
    public BaseRespVo wxCommentList(ListCondition listCondition) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map map = wxTopicService.wxUserComment(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;

    }

    /**
     * 专题评论添加
     */

    /**
     * 功能：
     * 发表评论
     *
     * 请求体：
     * content: "1"
     * hasPicture: true
     * picUrls: ["http://192.168.2.100:8081/wx/c3st.png"]
     * star: 5
     * type: 1
     * valueId: "314"
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"id": 1032,
     * 		"valueId": 314,
     * 		"type": 1,
     * 		"content": "1",
     * 		"userId": 1,
     * 		"hasPicture": true,
     * 		"picUrls": ["http://192.168.2.100:8081/wx/c3st.png"],
     * 		"star": 5,
     * 		"addTime": "2020-01-01 20:29:11",
     * 		"updateTime": "2020-01-01 20:29:11"
     *        },
     * 	"errmsg": "成功"
     * }
     * post请求
     */
    @RequestMapping("wx/comment/post")
    public BaseRespVo wxCommentPost(@RequestBody Comment comment) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Comment comment1 = wxTopicService.wxCommentPost(comment);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("添加成功");
        baseRespVo.setData(comment1);
        return baseRespVo;

    }
}
