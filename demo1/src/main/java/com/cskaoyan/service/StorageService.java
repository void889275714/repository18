package com.cskaoyan.service;

import com.cskaoyan.bean.Storage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

     int  insertStorage(Storage storage);

     Storage queryStorage(Storage storage);

     Storage storageFile(MultipartFile file) throws IOException;
}
