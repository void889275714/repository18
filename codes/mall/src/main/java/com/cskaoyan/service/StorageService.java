package com.cskaoyan.service;

import com.cskaoyan.bean.Storage;

public interface StorageService {
     int  insertStorage(Storage storage);

     Storage queryStorage(Storage storage);
}
