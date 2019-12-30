package com.cskaoyan.controller.promote;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.ListCondition;
import com.cskaoyan.bean.promote.Ad;
import com.cskaoyan.bean.promote.Coupon;
import com.cskaoyan.bean.promote.CouponExample;
import com.cskaoyan.bean.promote.topic;
import com.cskaoyan.service.promote.PromoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PromoteController {

    @Autowired
    PromoteService promoteService;




    /**
     * 广告管理
      */







    /**
     * 功能：
     * 查询所有广告
     *
     * 请求体：
     * page: 1
     * limit: 20
     * sort: add_time
     * order: desc
     *
     * 响应体：
     * {
     *     "errno": 0,
     *     "data": {
     *         "total": 2,
     *         "items": [
     *             {
     *                 "id": 12,
     *                 "name": "索引",
     *                 "link": "https://github.com/wang02181997",
     *                 "url": "http://192.168.2.100:8081/wx/storage/fetch/6c00ptr7zvbx4undv64x.png",
     *                 "position": 1,
     *                 "content": "索引内容",
     *                 "enabled": true,
     *                 "addTime": "2019-12-26 21:54:12",
     *                 "updateTime": "2019-12-26 21:54:12",
     *                 "deleted": false
     *             }
     *         ]
     *     },
     *     "errmsg": "成功"
     * }
     * @param page
     * @param limit
     * @param add_time
     * @param desc
     * @return
     */
    @RequestMapping("admin/ad/list")
    public BaseRespVo adList(ListCondition listCondition) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        if (listCondition == null) {
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("请求错误！");
        } else {
            //如果请求体中的name和content都为空，
            //即查询框中没有输入字符，执行全部显示
            Map<String,Object> map = promoteService.selectAllAds(listCondition);
            //map.put("total",counts);
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("查询成功！");
            baseRespVo.setData(map);
        }
        return baseRespVo;
    }

    /**
     * 功能：
     * 编辑广告
     *
     * 请求体：
     * id: 30
     * name: "pikapikaa"
     * link: ""
     * url: "http://192.168.2.100:8081/wx/storage/fetch/s0djpve8pxq876jr0pgn.gif"
     * position: 1
     * content: "草鸡"
     * enabled: true
     * addTime: "2019-12-29 21:47:02"
     * updateTime: "2019-12-30 01:10:49"
     * deleted: false
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		编辑后的数据
     *        },
     * 	"errmsg": "成功"
     * }
     */

    @RequestMapping("admin/ad/update")
    public BaseRespVo updateAd(@RequestBody Ad ad) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Ad ad1 = promoteService.updateAd(ad);
        if (ad1.equals(ad)) {
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("更改失败");
        } else {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("更改成功");
            baseRespVo.setData(ad1);
        }
        return baseRespVo;
    }

    /**
     * 功能：
     * 新增广告
     *
     * 请求体：
     * name: "狼人杀"
     * content: "王道狼人杀"
     * url: "http://192.168.2.100:8081/wx/storage/fetch/uyb79abqrvfs2d0xzb2b.jpg"
     * position: 1
     * enabled: true
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"id": 31,
     * 		新增的广告数据
     *        },
     * 	"errmsg": "成功"
     * }
     */

    @RequestMapping("admin/ad/create")
    public BaseRespVo createAd(@RequestBody Ad ad) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Ad ad1 = promoteService.createAd(ad);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("新增成功");
        baseRespVo.setData(ad1);
        return baseRespVo;

    }

    /**
     * 功能：
     * 表面删除广告
     *
     * 请求体：
     * id: 12
     * name: "索引1"
     * link: "https://github.com/wang02181997"
     * url: "http://192.168.2.100:8081/wx/storage/fetch/6c00ptr7zvbx4undv64x.png"
     * position: 1
     * content: "索引内容"
     * enabled: true
     * addTime: "2019-12-26 21:54:12"
     * updateTime: "2019-12-27 03:04:36"
     * deleted: false
     *
     * 响应体：
     * {"errno":0,"errmsg":"成功"}
     */

    @RequestMapping("admin/ad/delete")
    public BaseRespVo deleteAd(@RequestBody Ad ad) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        ad.setDeleted(true);

        if (ad == null) {
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("请求错误！");
        } else {
            int delete = promoteService.deleteAd(ad);
            if (delete == 1) {
                baseRespVo.setErrno(0);
                baseRespVo.setErrmsg("成功");
                baseRespVo.setData(delete);
            } else {
                baseRespVo.setErrno(1);
                baseRespVo.setErrmsg("删除失败");
            }

        }
        return baseRespVo;
    }




/**
 * 优惠券管理
  */






    /**
     * 功能：
     * 显示优惠券
     *
     * 请求体：
     * page: 1
     * limit: 20
     * sort: add_time
     * order: desc
     *
     * 响应体：
     * {
     *     "errno": 0,
     *     "data": {
     *         "total": 9,
     *         "items": [
     *             {
     *                 "id": 19,
     *                 "name": "闪电发货",
     *                 "desc": "",
     *                 "tag": "",
     *                 "total": 0,
     *                 "discount": 0,
     *                 "min": 0,
     *                 "limit": 1,
     *                 "type": 0,
     *                 "status": 0,
     *                 "goodsType": 0,
     *                 "goodsValue": [],
     *                 "timeType": 0,
     *                 "days": 0,
     *                 "addTime": "2019-12-27 07:23:14",
     *                 "updateTime": "2019-12-27 07:23:14",
     *                 "deleted": false
     *             },
     *             {
     *                 "id": 18,
     *                 "name": "快乐你我他",
     *                 "desc": "快乐你我他",
     *                 "tag": "222",
     *                 "total": 222,
     *                 "discount": 22,
     *                 "min": 22,
     *                 "limit": 1,
     *                 "type": 0,
     *                 "status": 0,
     *                 "goodsType": 0,
     *                 "goodsValue": [],
     *                 "timeType": 0,
     *                 "days": 22,
     *                 "addTime": "2019-12-27 07:20:36",
     *                 "updateTime": "2019-12-27 07:20:36",
     *                 "deleted": false
     *             }
     *         ]
     *     },
     *     "errmsg": "成功"
     * }
     */
    @RequestMapping("admin/coupon/list")
    public BaseRespVo couponList(ListCondition listCondition) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> couponMap = promoteService.selectCouponLists(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("查找成功！");
        baseRespVo.setData(couponMap);
        return baseRespVo;
    }

    /**
     * 功能：
     * 优惠券管理添加
     *
     * 请求体：
     * name: "肯德基全家桶减5元"
     * desc: "无"
     * tag: "肯德基"
     * total: "4"
     * discount: "2"
     * min: "1"
     * limit: "3"
     * type: 0
     * status: 0
     * goodsType: 0
     * goodsValue: []
     * timeType: 0
     * days: "5"
     * startTime: null
     * endTime: null
     *
     * 响应体：
     * {
     *     "errno": 0,
     *     "data": {
     *         "id": 24,
     *         添加的数据
     *     },
     *     "errmsg": "成功"
     * }
     * post请求
     */
    @RequestMapping("admin/coupon/create")
    public BaseRespVo createCoupon(@RequestBody Coupon coupon) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Coupon coupon1 = promoteService.createCoupon(coupon);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("新增成功");
        baseRespVo.setData(coupon1);
        return baseRespVo;

    }

    /**
     * 功能：
     * 优惠券详情页下方内容
     * 通过优惠券ID查找领取该券的用户
     *
     * 请求体：
     * page: 1
     * limit: 20
     * couponId: 3
     * sort: add_time
     * order: desc
     *
     * 响应体：
     * {
     *     "errno": 0,
     *     "data": {
     *         "total": 0,
     *         "items": [
     *         {}，
     *         {}
     *         ]
     *     },
     *     "errmsg": "成功"
     * }
     * @param listCondition
     * @return
     */
    @RequestMapping("admin/coupon/listuser")
    public BaseRespVo selectDetail(ListCondition listCondition) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        int couponId = listCondition.getCouponId();
        Map<String,Object> map = promoteService.selectUsersByCouponId(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("查询成功");
        baseRespVo.setData(map);
        return baseRespVo;

    }

    /**
     * 功能：
     * 显示详情页下半部分
     *
     * 请求体：
     * id: 3
     *
     * 响应体：
     * {
     *     "errno": 0,
     *     "data": {
     *         单条优惠券数据
     *     },
     *     "errmsg": "成功"
     * }
     *
     * 注意：
     * typeHandler转换格式
     * 返回格式和前端一致
     * @param coupon
     * @return
     */
    @RequestMapping("admin/coupon/read")
    public BaseRespVo selectCouponRead(Integer id) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Coupon coupon1 = promoteService.selectCouponById(id);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("查询成功");
        baseRespVo.setData(coupon1);
        return baseRespVo;
    }

    /**
     * 功能：
     * 删除优惠券
     *
     * 请求体：
     * 	id: 19
     * 	name: "闪电发货"
     * 	desc: ""
     * 	tag: ""
     * 	total: 0
     * 	discount: 0
     * 	min: 0
     * 	limit: 1
     * 	type: 0
     * 	status: 0
     * 	goodsType: 0
     * 	goodsValue: []
     * 	timeType: 0
     * 	days: 0
     * 	addTime: "2019-12-27 07:23:14"
     *	updateTime: "2019-12-27 07:23:14"
     * 	deleted: false
     *
     * 响应体：
     * {
     *     "errno": 0,
     *     "errmsg": "成功"
     * }
     * @param coupon
     * @return
     */
    @RequestMapping("admin/coupon/delete")
    public BaseRespVo deleteCoupon(@RequestBody Coupon coupon) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        coupon.setDeleted(true);
        int delete = promoteService.deleteCoupon(coupon);
        if (delete == 1) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        } else {
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("删除失败");
        }
        return baseRespVo;
    }

    /**
     * 功能：
     * 编辑优惠券
     *
     * 请求体：
     * id: 2
     * name: "限时满减券"
     * desc: "全场通用"
     * tag: "无限制"
     * total: 0
     * discount: "12"
     * min: 99
     * limit: 1
     * type: 0
     * status: 0
     * goodsType: 0
     * goodsValue: []
     * timeType: 0
     * days: 10
     * addTime: "2018-01-31 19:00:00"
     * updateTime: "2019-12-27 21:54:26"
     * deleted: false
     * daysType: 0
     *
     * 响应体：
     *     {
     *     更改后的优惠券数据
     *     },
     *     "errmsg": "成功"
     * }
     *
     * post请求
     */

    @RequestMapping("admin/coupon/update")
    public BaseRespVo updateCoupon(@RequestBody Coupon coupon) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Coupon coupon1 = promoteService.updateCoupon(coupon);
        if (coupon1.equals(coupon)) {
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("更改失败");
        } else {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("更改成功");
            baseRespVo.setData(coupon1);
        }
        return baseRespVo;
    }

    /**
     * 专题管理
     */

    /**
     * 功能：
     * 显示专题管理和模糊查询
     *
     * 请求体：
     * page: 1
     * limit: 20
     * sort: add_time
     * order: desc
     *
     * 响应体：
     *    {
     *     "errno": 0,
     *     "data": {
     *         "total": 21,
     *         "items": [
     *             多个专题数据list
     *         ]
     *     },
     *     "errmsg": "成功"
     * }
     */

    @RequestMapping("admin/topic/list")
    public BaseRespVo selevtTopices(ListCondition listCondition) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String, Object> map = promoteService.selectTopices(listCondition);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("查询成功");
        baseRespVo.setData(map);
        return  baseRespVo;

    }

    /**
     * 功能：
     * 删除专题
     *
     * 请求体：
     * id: 324
     * title: "12"
     * subtitle: "12"
     * price: 12
     * readCount: "12"
     * picUrl: "http://192.168.2.100:8081/wx/storage/fetch/jqupp65gnk3299oyrbiw.png"
     * sortOrder: 100
     * goods: []
     * addTime: "2019-12-28 07:00:59"
     * updateTime: "2019-12-28 07:00:59"
     * deleted: false
     * content: "<p><img src="http://192.168.2.100:8081/wx/storage/fetch/mir24e2v3fwgz8jhgd0f.png" alt="12" width="21" height="335" /></p>"
     *
     * 响应体：
     * {
     *     "errno": 0,
     *     "errmsg": "成功"
     * }
     * post 请求
     */

    @RequestMapping("admin/topic/delete")
    public BaseRespVo deleteTopic(@RequestBody topic toPic) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        toPic.setDeleted(true);
        int deleteTopic = promoteService.deleteTopic(toPic);
        if (deleteTopic == 0) {
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("删除失败");
        } else {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("删除成功");
            baseRespVo.setData(deleteTopic);
        }
        return baseRespVo;
    }

    /**
     * 功能：
     * 编辑专题
     *
     * 请求体：
     * id: 327
     * title: "12"
     * subtitle: "12"
     * price: 12
     * readCount: "12"
     * picUrl: "http://192.168.2.100:8081/wx/storage/fetch/corsmgk7nbl2irrq4c6u.png"
     * sortOrder: 100
     * goods: []
     * addTime: "2019-12-28 08:05:23"
     * updateTime: "2019-12-28 08:33:42"
     * deleted: false
     * content: "<p><img src="http://192.168.2.100:8081/wx/storage/fetch/v2swjlx2vmrg7nlgmh16.png" alt="" width="825" height="335" /></p>"
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 	  返回编辑后的专题信息
     *        },
     * 	"errmsg": "成功"
     * }
     *
     * post请求
     */

    @RequestMapping("admin/topic/update")
    public BaseRespVo update(@RequestBody topic toPic) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        topic toPic1 = promoteService.updateTopic(toPic);
        if (toPic1.equals(toPic)) {
            baseRespVo.setErrno(500);
            baseRespVo.setErrmsg("更改失败");
        } else {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("更改成功");
            baseRespVo.setData(toPic1);
        }
        return baseRespVo;
    }

    /**
     * 功能：
     * 添加专题
     *
     * 请求体：
     * subtitle: "王道狼人杀"
     * picUrl: "http://192.168.2.100:8081/wx/storage/fetch/9yp8nsi82os57a8kouzf.jpg"
     * content: "<p><img src="http://192.168.2.100:8081/wx/storage/fetch/snla4l9svgqsgrjs2439.png" alt="狼" width="268" height="268" /></p>"
     * price: "29.98"
     * readCount: "109"
     * goods: []
     * title: "狼人杀"
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"id": 329,
     * 		  添加的专题数据
     *        },
     * 	"errmsg": "成功"
     * }
     *
     * post请求
     */

    @RequestMapping("admin/topic/create")
    public BaseRespVo createTopic(@RequestBody topic toPic) {

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        topic toPic1 = promoteService.createTopic(toPic);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("新增成功");
        baseRespVo.setData(toPic1);
        return baseRespVo;

    }
}
