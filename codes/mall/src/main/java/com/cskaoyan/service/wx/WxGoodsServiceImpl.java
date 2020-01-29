package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bruce
 */
@Service
public class WxGoodsServiceImpl implements com.cskaoyan.service.WxGoodsService {

    @Autowired
    CateGoryMapper cateGoryMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsAttributeMapper goods_attributeMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    GoodsSpecificationMapper specificationMapper;
    @Autowired
    Groupon_rulesMapper groupon_rulesMapper;
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    GoodsProductMapper goods_productMapper;

    /**
     * 展示商品的categories
     *
     * @param id
     * @return
     */
    @Override
    public Map queryCategory(int id) {
        Map map = new HashMap();
        CateGory curCategory = new CateGory();
        CateGory paCategory = new CateGory();
        List<CateGory> broCategry = new ArrayList<>();

        //得到逆向工程的bean
        CateGoryExample cateGoryExample = new CateGoryExample();
        CateGoryExample.Criteria criteria = cateGoryExample.createCriteria();
        //根据id去查pid
        CateGory cateGory = cateGoryMapper.selectByPrimaryKey(id);
        Integer pid = cateGory.getPid();

        //判断Pid
        if (pid == 0) {
            //没有parent 就是自己
            paCategory = cateGoryMapper.selectByPrimaryKey(id);
            // 查询brother category
            criteria.andPidEqualTo(id);
            broCategry = (ArrayList<CateGory>) cateGoryMapper.selectByExample(cateGoryExample);
            //得到current category
             curCategory = broCategry.get(0);
        } else {
            //得到current category
            curCategory = cateGory;
            // 查询brother category
            criteria.andPidEqualTo(pid);
            broCategry = (ArrayList<CateGory>) cateGoryMapper.selectByExample(cateGoryExample);
            //查询parent category,它的父categories的id 等于kid的pid
            paCategory = cateGoryMapper.selectByPrimaryKey(pid);
        }

        //放入map
        map.put("brotherCategory", broCategry);
        map.put("currentCategory", curCategory);
        map.put("parentCategory", paCategory);
        return map;
    }

    /**
     * 展示商品的列表
     *
     * @param listCondition
     * @return
     */
    @Override
    public Map queryGoodsList(ListCondition listCondition) {
        PageHelper.startPage(listCondition.getPage(), listCondition.getSize());
        //得到page的限制
        //查询与categoryid相同的商品信息
        // 查询商品表
        GoodsExample goodsExample = new GoodsExample();
        int categoryId = Integer.parseInt(listCondition.getCategoryId());
        goodsExample.createCriteria().andCategoryIdEqualTo(categoryId);
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);

        //查询到总数
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        long total = pageInfo.getTotal();

        HashMap<String, Object> map = new HashMap<>();
        map.put("count", total);
        map.put("filterCategoryList", null);
        map.put("goodsList", goods);
        return map;
    }

    /**
     * 计算商品总数
     *
     * @return
     */
    @Override
    public int countGoods() {
        long count = goodsMapper.countByExample(new GoodsExample());
        return (int) count;
    }

    /**
     * 商品详情的页面
     * @param id
     * @return
     */
    @Override
    public Map goodsDetail(int id) {
        //新建一个map
        HashMap<String, Object> map = new HashMap<>();
        //查询info
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        map.put("info",goods);

        //查询attribute
        GoodsAttributeExample goods_attributeExample = new GoodsAttributeExample();
        GoodsAttributeExample.Criteria criteria = goods_attributeExample.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        criteria.andDeletedEqualTo(false);
        List<GoodsAttribute> goodsAttributes = goods_attributeMapper.selectByExample(goods_attributeExample);
        map.put("attribute",goodsAttributes);

        //查询brand
        Goods goods1 = goodsMapper.selectByPrimaryKey(id);
        Brand brand = new Brand();
        brand = brandMapper.selectByPrimaryKey(goods1.getBrandId());
        map.put("brand",brand);

        //查询specification list
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        GoodsSpecificationExample.Criteria criteria1 = goodsSpecificationExample.createCriteria();
        //拼接查询条件
        criteria1.andDeletedEqualTo(false);
        criteria1.andGoodsIdEqualTo(id);
        List<GoodsSpecification> goods_specifications = specificationMapper.selectByExample(goodsSpecificationExample);
        ArrayList<Map> maps = new ArrayList<>();
        for (GoodsSpecification goodsSpecification : goods_specifications) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("name",goodsSpecification.getSpecification());
            ArrayList<Object> arrayList = new ArrayList<>();
            arrayList.add(goodsSpecification);
            map1.put("valueList",arrayList);
            maps.add(map1);
        }
        map.put("specificationList",maps);

        //查询Groupon
        Groupon_rulesExample groupon_rulesExample = new Groupon_rulesExample();
        Groupon_rulesExample.Criteria criteria2 = groupon_rulesExample.createCriteria();
        criteria2.andGoodsIdEqualTo(id);
        criteria2.andDeletedEqualTo(false);
        List<Groupon_rules> list = groupon_rulesMapper.selectByExample(groupon_rulesExample);
        map.put("groupon",list);

        //查询issue
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andDeletedEqualTo(false);
        List<Issue> issueList = issueMapper.selectByExample(issueExample);
        map.put("issue",issueList);

        //collect
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria3 = collectExample.createCriteria();
        criteria3.andDeletedEqualTo(false);
        criteria3.andValueIdEqualTo(id);
        long count = collectMapper.countByExample(collectExample);
        map.put("userHasCollect",count);

        //shareImg
        map.put("shareImage","");

        //comment
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria4 = commentExample.createCriteria();
        criteria4.andDeletedEqualTo(false);
        criteria4.andValueIdEqualTo(id);
        HashMap<String, Object> map1 = new HashMap<>();
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        long count1 = commentMapper.countByExample(commentExample);
        map1.put("data",comments);
        map1.put("count",count1);
        map.put("comment",map1);

        //productList
        GoodsProductExample goods_productExample = new GoodsProductExample();
        GoodsProductExample.Criteria criteria5 = goods_productExample.createCriteria();
        criteria5.andDeletedEqualTo(false);
        criteria5.andGoodsIdEqualTo(id);
        List<GoodsProduct> goods_products = goods_productMapper.selectByExample(goods_productExample);
        map.put("productList",goods_products);


        return map;
    }

    @Override
    public Map queryRelated(int id) {
        HashMap<String, List> map = new HashMap<>();
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        ArrayList<Object> list = new ArrayList<>();
        while (list.size() < 11){
            int a = (int) Math.floor(Math.random()*goods.size());
            if (!list.contains(a)){
                list.add(a);
                list.add(0,goods.get(a));
            }
        }
        while (list.size() > 6){
            list.remove(list.size()-1);
        }
        map.put("goodsList",list);
        return map;
    }


}
