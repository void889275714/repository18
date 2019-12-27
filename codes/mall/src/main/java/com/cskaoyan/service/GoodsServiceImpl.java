package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Date 2019-12-26 22:20
 * @Created by ouyangfan
 */
@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;
    @Autowired
    CateGoryMapper cateGoryMapper;
    /**
     * 根据条件查询商品信息
     * @param listConditon 封装了条件的对象
     * @return
     */
    @Override
    public List<Goods> queryAllGoods(ListConditon listConditon) {
        //开启分页
        PageHelper.startPage(listConditon.getPage(), listConditon.getLimit());
        GoodsExample goodsExample = new GoodsExample();
        //添加排序条件
        goodsExample.setOrderByClause(listConditon.getSort() + " " + listConditon.getOrder());
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        //获取查询条件
        String goodsSn = listConditon.getGoodsSn();
        String name = listConditon.getName();
        //查询条件不为空且不为null时，添加查询条件
        if(goodsSn != null && goodsSn.length() > 0) {
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if(name != null && name.length() > 0){
            criteria.andNameLike("%" + name + "%");
        }
        return goodsMapper.selectByExample(goodsExample);
    }

    /**
     * 查询商品总数量
     * @return
     */
    @Override
    public Long countGoods() {
        return goodsMapper.countByExample(new GoodsExample());
    }

    /**
     * 根据商品id查询goods表信息+attributes表信息+specifications表信息+products表信息+category表信息
     * @param id
     * @return
     */
    @Override
    public GoodsDetail queryGoodsDetail(int id) {
        GoodsDetail goodsDetail = new GoodsDetail();

        //根据id查询goods表信息
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        goodsDetail.setGoods(goods);

        //取出category_id
        Integer categoryId = goods.getCategoryId();
        //根据categoryId查出pid
        CateGory cateGory = cateGoryMapper.selectByPrimaryKey(categoryId);
        Integer pid = cateGory.getPid();
        List<Integer> categorys = new ArrayList<>();
        categorys.add(categoryId);
        categorys.add(pid);
        goodsDetail.setCategorys(categorys);

        //根据商品id查询goods_attribute表信息，返回一个GoodsAttribute对象的list
        GoodsAttributeExample attributeExample = new GoodsAttributeExample();
        attributeExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(attributeExample);
        goodsDetail.setGoodsAttributeList(goodsAttributes);

        //根据商品id查询goods_specification表信息，返回一个GoodsSpecification对象的list
        GoodsSpecificationExample specificationExample = new GoodsSpecificationExample();
        specificationExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(specificationExample);
        goodsDetail.setGoodsSpecifications(goodsSpecifications);

        //根据商品id查询goods_product表信息，返回一个GoodsProduct对象的list
        GoodsProductExample productExample = new GoodsProductExample();
        productExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(productExample);
        goodsDetail.setGoodsProductList(goodsProducts);
        return goodsDetail;
    }
}
