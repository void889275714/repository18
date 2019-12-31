package com.cskaoyan.service;

import com.cskaoyan.bean.CateGory;
import com.cskaoyan.bean.CateGoryExample;
import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.bean.RegionExample;
import com.cskaoyan.mapper.CateGoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CateGoryServiceImpl implements CateGoryService{

    @Autowired
    CateGoryMapper cateGoryMapper;


    /**
     * 商品类目的显示
     * @return
     */
    @Override
    public List<CateGory> cateList() {

        CateGoryExample cateGoryExample = new CateGoryExample();
        cateGoryExample.createCriteria().andLevelEqualTo("L1").andDeletedEqualTo(false);

        List<CateGory> cateGories = cateGoryMapper.selectByExample(cateGoryExample);
        for (CateGory cateGory : cateGories) {
            CateGoryExample cateGoryExample1 = new CateGoryExample();
            CateGoryExample.Criteria criteria = cateGoryExample1.createCriteria();
            criteria.andPidEqualTo(cateGory.getId());
            criteria.andLevelEqualTo("L2");
            List<CateGory> cateGories1 = cateGoryMapper.selectByExample(cateGoryExample1);
            cateGory.setChildren(cateGories1);
        }
        return cateGories;
    }


    /**
     * 商品类目等级为L1的显示
     * @return
     */
    @Override
    public List<Map<String, Object>> cateL1List() {
        CateGoryExample cateGoryExample = new CateGoryExample();
        cateGoryExample.createCriteria().andLevelEqualTo("L1");
        List<CateGory> cateGories = cateGoryMapper.selectByExample(cateGoryExample);
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        for (CateGory cateGory : cateGories) {
            HashMap<String, Object> map = new HashMap<>();
            Integer id = cateGory.getId();
            String name = cateGory.getName();
            map.put("value",id);
            map.put("label",name);
            data.add(map);
        }
        return data;
    }


    /**
     * 新增一个类目
     * @param cateGory
     * @return
     */
    @Override
    public CateGory createCategory(CateGory cateGory) {
        cateGoryMapper.insert(cateGory);
        String picUrl = cateGory.getPicUrl();
        CateGoryExample cateGoryExample = new CateGoryExample();
        cateGoryExample.createCriteria().andPicUrlEqualTo(picUrl);
        List<CateGory> cateGories = cateGoryMapper.selectByExample(cateGoryExample);
        Integer id = null;
        for (CateGory gory : cateGories) {
            id = gory.getId();
            break;
        }
        CateGory cateGory1 = cateGoryMapper.selectByPrimaryKey(id);
        return cateGory1;
    }


    /**
     * 更新类目
     * @param cateGory
     * @return
     */
    @Override
    public CateGory updateCateGory(CateGory cateGory) {
        //修改就这一句话
        cateGoryMapper.updateByPrimaryKey(cateGory);
        Integer id = cateGory.getId();
        CateGory cateGory1 = cateGoryMapper.selectByPrimaryKey(id);
        return cateGory1;
    }

    /**
     * 删除
     * @param cateGory
     * @return
     */
    @Override
    public boolean deleteCateGory(CateGory cateGory) {
        cateGory.setDeleted(true);
        int update = cateGoryMapper.updateByPrimaryKey(cateGory);
        if (update > 0) {
            return true;
        }
        return false;
    }


    /**
     * wx/catalog/current?id=1005002
     * Wx
     * @return
     */
    @Override
    public Map<String, Object> wxCateList(int id) {
        CateGoryExample cateGoryExample = new CateGoryExample();
        cateGoryExample.createCriteria().andLevelEqualTo("L1").andIdEqualTo(id);
        List<CateGory> cateGories = cateGoryMapper.selectByExample(cateGoryExample);
        HashMap<String, Object> map = new HashMap<>();
        //设置为map参数里面的currentCategory
        CateGory cateGory1 = cateGories.get(0);
        map.put("currentCategory",cateGory1);
        CateGoryExample cateGoryExample1 = new CateGoryExample();
        cateGoryExample1.createCriteria().andLevelEqualTo("L2").andIdEqualTo(id);
        List<CateGory> cateGories1 = cateGoryMapper.selectByExample(cateGoryExample1);
        map.put("currentSubCategory",cateGories1);
        return map;
    }


}
