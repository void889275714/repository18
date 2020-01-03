package com.cskaoyan.service.wx;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Collect;
import com.cskaoyan.bean.CollectExample;
import com.cskaoyan.bean.User;
import com.cskaoyan.mapper.CollectMapper;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxCollectServiceImpl implements WxCollectService{

    @Autowired
    CollectMapper collectMapper;


    /**
     *  CollectAddOrDelete: WxApiRoot + 'collect/addordelete', //添加或取消收藏
     * @param type
     * @param valueid
     * @return
     */
    @Override
    public String addordelete(byte type, int valueid) {
        //获取登陆后的User信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Integer id = user.getId();

        //创建插入的信息

        Collect collect = new Collect();
        collect.setUserId(id);
        collect.setValueId(valueid);
        collect.setType(type);

        String result = null;

        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andValueIdEqualTo(valueid).andDeletedEqualTo(false);


        List<Collect> collects = collectMapper.selectByExample(collectExample);
        if (collects.size() > 0) {
            collect.setDeleted(true);
            collect.setUpdateTime(new Date());
            result = "delete";
            collectMapper.updateByPrimaryKey(collect);
        }else {
            collect.setAddTime(new Date());
            collect.setUpdateTime(new Date());
            collect.setDeleted(false);
            result = "add";
            collectMapper.insert(collect);
        }

        return result;
    }

    /**
     * 显示收藏列表
     * @param type
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map<String, Object> showCollectList(byte type, int page, int size) {
        PageHelper.startPage(page,size);
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andTypeEqualTo(type);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer id = user.getId();
        criteria.andUserIdEqualTo(id);
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        long total = collectMapper.countByExample(collectExample);
        int totalPages = (int)((total/size)+1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("totalPages",totalPages);
        map.put("collectList",collects);
        return map;
    }


}
