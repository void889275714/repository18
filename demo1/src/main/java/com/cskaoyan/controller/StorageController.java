package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Storage;
import com.cskaoyan.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Description TODO
 * @Date 2019-12-27 19:41
 * @Created by ouyangfan
 */
@RestController
public class StorageController {

    @Autowired
    StorageService storageService;

    /**
     * 处理文件上传
     * 请求体：二进制文件
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"id": 141,
     * 		"key": "wrgotrn5gphj4hot3b1h.jpg",
     * 		"name": "1571208195203.jpg",
     * 		"type": "image/jpeg",
     * 		"size": 229478,
     * 		"url": "http://192.168.2.100:8081/wx/storage/fetch/wt3b1h.jpg",
     * 		"addTime": "2019-12-27 06:31:06",
     * 		"updateTime": "2019-12-27 06:31:06"
     *        },
     * 	"errmsg": "成功"
     * }
     *
     * @return
     */
    @RequestMapping("wx/storage/create")
    public BaseRespVo fileUpload(MultipartFile file) throws IOException {
        BaseRespVo baseRespVo = new BaseRespVo();
        Storage storage = storageService.storageFile(file);
        if(storage != null) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
            baseRespVo.setData(storage);
        }
        return baseRespVo;
    }

    /**
     * 功能：
     * 留言/评论上传图片
     *
     * 请求体：
     * 无
     *
     * 响应体：
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"id": 457,
     * 		"key": "c3st.png",
     * 		"name": "touristappid.png",
     * 		"type": "image/png",
     * 		"size": 57851,
     * 		"url": "http://192.168.2.100:8081/wx/3st.png",
     * 		"addTime": "2020-01-01 20:29:01",
     * 		"updateTime": "2020-01-01 20:29:01"
     *        },
     * 	"errmsg": "上传成功"
     * }
     *
     * post请求
     */
    @RequestMapping("wx/storage/upload")
    public BaseRespVo wxFileUpload(MultipartFile file) throws IOException {
        BaseRespVo baseRespVo = new BaseRespVo();
        Storage storage = storageService.storageFile(file);
        if(storage != null) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
            baseRespVo.setData(storage);
        }
        return baseRespVo;
    }
}
