package com.stylefeng.guns.rest.modular.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class LocalCacheService {

    private Cache<String,Object> cache;

    @PostConstruct
    public void init(){

        cache = CacheBuilder.newBuilder()
                //设置初始容量
                .initialCapacity(100)
                //设置最大容量
                .maximumSize(200)
                //设置缓存时间
                //数据写入后多少时间过期  固定的
                .expireAfterWrite(5, TimeUnit.MINUTES)
                //数据被访问后多少时间过期  可以被刷新
                //.expireAfterAccess()
                .build();
    }


    //存数据的方法
    public void set(String key,Object value){
        cache.put(key,value);
    }


    //取数据的方法
    public Object get(String key){
        Object ifPresent = cache.getIfPresent(key);
        return ifPresent;
    }


}
