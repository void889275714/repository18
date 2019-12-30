package com.cskaoyan.service;

import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.bean.Storage;
import com.cskaoyan.bean.StorageExample;
import com.cskaoyan.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

}
