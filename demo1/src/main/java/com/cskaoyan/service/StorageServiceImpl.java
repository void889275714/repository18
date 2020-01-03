package com.cskaoyan.service;

import com.cskaoyan.bean.Storage;
import com.cskaoyan.bean.StorageExample;
import com.cskaoyan.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class StorageServiceImpl implements StorageService{

    @Autowired
    StorageMapper storageMapper;


    /**
     * 新增数据,返回此数据
     * @param storage
     * @return
     */
    @Override
    public int insertStorage(Storage storage) {
        int insert = storageMapper.insert(storage);
        return insert;
    }

    @Override
    public Storage queryStorage(Storage storage) {
        StorageExample storageExample = new StorageExample();
        String url = storage.getUrl();
        storageExample.createCriteria().andUrlEqualTo(url);

        List<Storage> storages = storageMapper.selectByExample(storageExample);
        Integer id = null;
        for (Storage storage1 : storages) {
           id = storage1.getId();
           break;
        }

        Storage data = storageMapper.selectByPrimaryKey(id);
        return data;
    }


    /**
     * 将file中的参数取出来，封装到storage对象中，再调用mapper持久化数据
     * @param file
     * @return
     */
    @Override
    public Storage storageFile(MultipartFile file) throws IOException {
        //取出file中的各个属性
        long size = file.getSize();
        String contentType = file.getContentType();
        String filename = file.getOriginalFilename();
        //定义储存位置
        //你们window系统需要把下面这行代码的注释放开
        //String path = ResourceUtils.getURL("classpath:").getPath().replace("%20"," ").replace('/', '\\');
        String path = ResourceUtils.getURL("classpath:").getPath().replace("%20", " ");
        File fileDestination = new File(path + "/static/",filename);
        file.transferTo(fileDestination);
        Storage storage = new Storage();
        storage.setKey(UUID.randomUUID().toString().replaceAll("-", ""));
        storage.setName(filename);
        storage.setType(contentType);
        storage.setSize((int) size);
        storage.setUrl("http://localhost:8081/" + filename);
        storage.setAddTime(new Date());
        storage.setUpdateTime(new Date());
        storage.setDeleted(false);
        //持久化数据
        storageMapper.insert(storage);
        return storage;
    }

}
