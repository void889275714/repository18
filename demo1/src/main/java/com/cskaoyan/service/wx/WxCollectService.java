package com.cskaoyan.service.wx;

import java.util.Map;

public interface WxCollectService {
    String addordelete(byte type,int valueid);

    Map<String,Object> showCollectList(byte type,int page,int size);
}
