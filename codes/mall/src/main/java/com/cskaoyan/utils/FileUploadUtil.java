package com.cskaoyan.utils;

import com.cskaoyan.bean.Storage;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;


/**
 *  接收前端传过来的file参数，如下代买调用即可
 *   public BaseRespVo mallFileUpload(MultipartFile file) throws IOException {
 *   Storage storage = FileUploadUtil.uploadReturnStorage(file);
 *   }
 */
public class FileUploadUtil {

    public static Storage uploadReturnStorage(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();

        //创建storage中key的内容为uuid
        int i = filename.lastIndexOf(".");
        String substring = filename.substring(i);
        uuid += substring;

        //文件上传,如果写的是target目录的路径的话需要先编译，如果没有static文件请手动添加
        //仅仅需要修改自己本地储存的路径
        String url = "D:\\project21226\\code\\mall\\day1\\demo1\\target\\classes\\static";
        File file1 = new File(url,uuid);
        file.transferTo(file1);

        //将信息封装到javabean中
        Storage storage = new Storage();
        storage.setKey(uuid);
        storage.setName(filename);
        storage.setSize((int) size);
        storage.setUrl("http://localhost:8081/"+uuid);
        storage.setType(contentType);
        storage.setDeleted(false);
        Date date = new Date();
        storage.setAddTime(date);
        storage.setUpdateTime(date);
        return storage;
    }
}
