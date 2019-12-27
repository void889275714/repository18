package com.cskaoyan.service;

import com.cskaoyan.bean.Storage;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Description TODO
 * @Date 2019-12-27 20:20
 * @Created by ouyangfan
 */
public interface StorageService {
    Storage storageFile(MultipartFile file) throws IOException;
}
