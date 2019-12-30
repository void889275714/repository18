package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.exception.ItemAlreadyExistException;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    GoodsAttributeService goodsAttributeService;
    @Autowired
    GoodsProductServiceImpl goodsProductService;
    @Autowired
    GoodsSpecificationService goodsSpecificationService;
    /**
     * 根据条件查询商品信息
     * @param listCondition 封装了条件的对象
     * @return
     */
    @Override
    public Map queryAllGoods(ListCondition listCondition) {
        //开启分页
        PageHelper.startPage(listCondition.getPage(), listCondition.getLimit());
        GoodsExample goodsExample = new GoodsExample();
        //添加排序条件
        goodsExample.setOrderByClause(listCondition.getSort() + " " + listCondition.getOrder());
        GoodsExample.Criteria criteria = goodsExample.createCriteria().andDeletedEqualTo(false);
        //获取查询条件
        String goodsSn = listCondition.getGoodsSn();
        String name = listCondition.getName();
        //查询条件不为空且不为null时，添加查询条件
        if(goodsSn != null && goodsSn.length() > 0) {
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if(name != null && name.length() > 0){
            criteria.andNameLike("%" + name + "%");
        }
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        int total2 = goods.size();
        long total = goodsMapper.countByExample(goodsExample);
        Map map = new HashMap();
        map.put("total", (int) total);
        map.put("items", goods);
        return map;
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
        goodsDetail.setCategoryIds(categorys);

        //根据商品id查询goods_attribute表信息，返回一个GoodsAttribute对象的list
        GoodsAttributeExample attributeExample = new GoodsAttributeExample();
        attributeExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(attributeExample);
        goodsDetail.setAttributes(goodsAttributes);

        //根据商品id查询goods_specification表信息，返回一个GoodsSpecification对象的list
        GoodsSpecificationExample specificationExample = new GoodsSpecificationExample();
        specificationExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(specificationExample);
        goodsDetail.setSpecifications(goodsSpecifications);

        //根据商品id查询goods_product表信息，返回一个GoodsProduct对象的list
        GoodsProductExample productExample = new GoodsProductExample();
        productExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(productExample);
        goodsDetail.setProducts(goodsProducts);
        return goodsDetail;
    }

    /**
     * 查询所有类别及其子类别信息和品牌信息
     * @return
     */
    @Override
    public Map<String, Object> queryCatAndBrand() {
        //查询品牌信息,封装到BrandForReturnBean的集合中
        List<BrandForReturnBean> brandList = brandMapper.selectBrandForReturn();
        //查询类别表中的所有的父类别，即pid字段为0的类别
        List<CateGoryForReturnBean> categoryList = cateGoryMapper.selectFatherAndSonCategory();
        Map map = new HashMap();
        map.put("categoryList", categoryList);
        map.put("brandList", brandList);
        return map;
    }

    /**
     * 根据传过来的GoodsDetail中的更新goods、goods_specification、goods_attribute、goods_product四张表中的信息
     * @param goodsDetail
     * @return
     */
    @Override
    public void updateGoodsDetail(GoodsDetail goodsDetail) {
        //更新goods表
        Goods goods = goodsDetail.getGoods();
        int updateForGoods = goodsMapper.updateByPrimaryKeySelective(goods);
        //goods_attribute表中删除、更新或者插入数据
        goodsAttributeService.updateGoodsDetailForAttribute(goodsDetail);
        //goods_product表中删除、更新或者插入数据
        goodsProductService.updateGoodsDetailForProduct(goodsDetail);
        //goods_specification表中删除、更新或者插入数据
        goodsSpecificationService.updateGoodsDetailForSpecification(goodsDetail);
    }

    /**
     * 添加商品，四张表都需要更改
     * @param goodsDetail
     */
    @Override
    public void createGoods(GoodsDetail goodsDetail) throws ItemAlreadyExistException {
        //将数据插入goods表
        Goods goods = goodsDetail.getGoods();
        //验证商品数据是否已经存在
        String goodsSn = goods.getGoodsSn();
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andGoodsSnEqualTo(goodsSn).andDeletedEqualTo(false);
        long count = goodsMapper.countByExample(goodsExample);
        if(count == 1L){
            throw new ItemAlreadyExistException("商品名已经存在！");
        }
        goods.setAddTime(new Date());
        goods.setUpdateTime(new Date());
        goods.setDeleted(false);
        goodsMapper.insertSelective(goods);
        //将刚插入商品的id更新一下
        goodsDetail.setGoods(goods);
        //goods_attribute表中插入数据
        goodsAttributeService.updateGoodsDetailForAttribute(goodsDetail);
        //goods_product表中插入数据
        goodsProductService.updateGoodsDetailForProduct(goodsDetail);
        //goods_specification表中插入数据
        goodsSpecificationService.updateGoodsDetailForSpecification(goodsDetail);
    }

    /**
     * 删除商品（将deleted改为true），四张表都需要更改
     * @param goods
     */
    @Override
    public void deleteGoods(Goods goods) {
        //更新goods表中的deleted
        goods.setDeleted(true);
        goods.setUpdateTime(new Date());
        goodsMapper.updateByPrimaryKeySelective(goods);
        Integer goodsId = goods.getId();
        //根据goodsId更新goods_attribute表中的deleted
        GoodsAttribute goodsAttribute = new GoodsAttribute();
        //设定更改项
        goodsAttribute.setDeleted(true);
        goodsAttribute.setUpdateTime(new Date());
        //设定条件对象
        GoodsAttributeExample attributeExample = new GoodsAttributeExample();
        attributeExample.createCriteria().andGoodsIdEqualTo(goodsId);
        goodsAttributeMapper.updateByExampleSelective(goodsAttribute, attributeExample);
        //根据goodsId更新goods_product表中的deleted
        GoodsProduct goodsProduct = new GoodsProduct();
        //设定更改项
        goodsProduct.setDeleted(true);
        goodsProduct.setUpdateTime(new Date());
        //设定条件对象
        GoodsProductExample productExample = new GoodsProductExample();
        productExample.createCriteria().andGoodsIdEqualTo(goodsId);
        goodsProductMapper.updateByExampleSelective(goodsProduct, productExample);
        //根据goodsId更新goods_specification表中的deleted
        GoodsSpecification goodsSpecification = new GoodsSpecification();
        //设定更改项
        goodsSpecification.setDeleted(true);
        goodsSpecification.setUpdateTime(new Date());
        //设定条件对象
        GoodsSpecificationExample specificationExample = new GoodsSpecificationExample();
        specificationExample.createCriteria().andGoodsIdEqualTo(goodsId);
        goodsSpecificationMapper.updateByExampleSelective(goodsSpecification, specificationExample);
    }
}
