package com.cskaoyan.service.wx;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.WxAddCart;
import com.cskaoyan.bean.WxCartIndex;
import com.cskaoyan.bean.WxProductId;
import com.cskaoyan.mapper.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WxCartServiceImpl implements WxCartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    Groupon_rulesMapper grouponRulesMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    CouponMapper couponMapper;

    /**
     * 展示用户的购物车页面
     *
     * @return
     */
    @Override
    public Map showIndex() {
        //获取这四个参数的值
        Short goodsCount = 0;
        BigDecimal goodsAmount = new BigDecimal("0");
        Short checkedGoodsCount = 0;
        BigDecimal checkedGoodsAmount = new BigDecimal("0");
        //获取登录的用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer id = user.getId();
        int gc = 0;
        int cgc = 0;
        int ga = 0;
        int cga = 0;
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(id).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        for (Cart cart : carts) {
            gc += cart.getNumber().intValue();
            ga += (cart.getNumber().intValue()) * (cart.getPrice().intValue());
        }

        CartExample cartExample2 = new CartExample();
        CartExample.Criteria criteria = cartExample2.createCriteria();
        //遍历选中的购物车中的商品
        criteria.andCheckedEqualTo(true).andDeletedEqualTo(false).andUserIdEqualTo(id);
        List<Cart> cartsChecked = cartMapper.selectByExample(cartExample2);
        for (Cart cart : cartsChecked) {
            cgc += cart.getNumber().intValue();
            cga += (cart.getNumber().intValue()) * (cart.getPrice().intValue());
        }

        HashMap hashMap = new HashMap();
        goodsCount = (short) gc;
        checkedGoodsCount = (short) cgc;
        goodsAmount = new BigDecimal("" + ga);
        checkedGoodsAmount = new BigDecimal("" + cga);
        WxCartIndex wxCartIndex = new WxCartIndex(goodsCount, checkedGoodsCount, goodsAmount, checkedGoodsAmount);
        hashMap.put("cartTotal", wxCartIndex);
        hashMap.put("cartList", carts);
        return hashMap;
    }

    /**
     * 添加商品到购物车
     *
     * @param wxAddCart
     * @return
     */
    @Override
    public int queryAddCart(WxAddCart wxAddCart) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        if (userId == null) {
            return -1;
        }
        Cart cart = null;

        //获得添加商品的数量
        Short number = wxAddCart.getNumber();

        //查询goods表
        Integer goodsId = wxAddCart.getGoodsId();
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);

        //查询goods product获得specification
        Integer productId = wxAddCart.getProductId();
        GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(productId);
        String[] specifications = goodsProduct.getSpecifications();
        String str = Arrays.toString(specifications);
        Date date = new Date();

        // 判断cart中是否有这个productId这个商品
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        //没有的话就加入购物车
        if (carts.size() == 0) {
            cart = new Cart(userId,
                    goodsId,
                    goods.getGoodsSn(),
                    goods.getName(),
                    productId,
                    goods.getRetailPrice(),
                    number,
                    str,
                    true,
                    goods.getPicUrl(),
                    date,
                    false);
        } else {
            //更新cart表中已存在商品的数量
            Cart cart1 = carts.get(0);
            int i = cart1.getNumber().intValue() + number.intValue();
            cart1.setNumber((short) i);
            CartExample cartExample1 = new CartExample();
            cartExample1.createCriteria().andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
            cartMapper.updateByExampleSelective(cart1, cartExample1);
        }
        //加入到购物车表
        cartMapper.insert(cart);

        CartExample cartExample1 = new CartExample();
        cartExample1.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userId);
        List<Cart> carts1 = cartMapper.selectByExample(cartExample1);
        //定义一个数
        int num = 0;
        for (Cart cart1 : carts1) {
            num += cart1.getNumber().intValue();
        }
        //获得总数
        Short count = (short) num;
        return count;
    }

    /**
     * 查询购物车的数量
     *
     * @return
     */
    @Override
    public int queryGoodsCount() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user != null) {
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(user.getId());
            List<Cart> carts = cartMapper.selectByExample(cartExample);
            int num = 0;
            for (Cart cart : carts) {
                num = cart.getNumber();
            }
            return num;
        }
        return 0;
    }

    /**
     * 点击编辑出现update方法
     *
     * @param cart
     */
    @Override
    public void update(Cart cart) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        Date date = new Date();
        cart.setUpdateTime(date);
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andIdEqualTo(cart.getId()).andUserIdEqualTo(userId);
        cartMapper.updateByExampleSelective(cart, cartExample);
    }

    /**
     * 删除购物车的商品
     *
     * @param wxProductId
     * @return
     */
    @Override
    public Map delete(WxProductId wxProductId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        //得到一个参数
        int[] productIds = wxProductId.getProductIds();
        for (int productId : productIds) {
            Cart cart = new Cart();
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andProductIdEqualTo(productId).andUserIdEqualTo(userId);
            cart.setDeleted(true);
            cartMapper.updateByExampleSelective(cart, cartExample);
        }
        return showIndex();
    }

    /**
     * 改变购物车里的商品状态
     *
     * @param wxProductId
     * @return
     */
    @Override
    public Map checkedStatus(WxProductId wxProductId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        int[] productIds = wxProductId.getProductIds();
        Date date = new Date();
        for (int productId : productIds) {
            Cart cart = new Cart();
            cart.setChecked(wxProductId.getIsChecked());
            cart.setUpdateTime(date);
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andProductIdEqualTo(productId).andUserIdEqualTo(userId);
            cartMapper.updateByExampleSelective(cart, cartExample);
        }
        // 调用index，返回一个map
        return showIndex();
    }

    /**
     * 立即下单的方法
     *
     * @return
     */
    @Override
    public int fastOrder(WxAddCart add) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return -1;
        }
        Integer userId = user.getId();
        //调用一个方法更新最后下单的
        int cartId = updateAfterFastAdd(add, userId);
        return cartId;
    }

    /**
     * 根据addressId查address表确定地址
     *      如果addressId=0，就是当前user_id的默认地址
     * 根据cartId到cart表中查询对应的goods_id
     *      如果cartId=0，那么就是当前user_id对应的所有checked为1的goods_id的集合
     * 根据grouponRulesId查groupon表中的对应goods_id的discount
     *      如果grouponRulesId=0，代表没有团购
     * 根据couponId查coupon表中的discount，但是要判断有没有满足满减条件
     *      如果couponId=0或者-1，代表没有优惠券
     * 计算goodsTotalPrice
     * @param checkOutBean
     * @return
     */
    @Override
    public CartCheckOutForReturn cartCheckOut(CartCheckOutBean checkOutBean) {
        //满88包邮
        Double hasFreightPrice = 88.0;

        Integer cartId = checkOutBean.getCartId();
        Integer addressId = checkOutBean.getAddressId();
        Integer grouponRulesId = checkOutBean.getGrouponRulesId();
        Integer couponId = checkOutBean.getCouponId();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        CartCheckOutForReturn cartCheckOutForReturn = new CartCheckOutForReturn();
        //设置addressId、couponId、grouponRulesId
        cartCheckOutForReturn.setAddressId(addressId);
        cartCheckOutForReturn.setCouponId(couponId);
        cartCheckOutForReturn.setGrouponRulesId(grouponRulesId);
        //根据addressId查address表确定地址,如果addressId=0，就是当前user_id的默认地址
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria addressExampleCriteria = addressExample.createCriteria();
        addressExampleCriteria.andDeletedEqualTo(false);
        if(addressId <= 0){
            //查询默认地址
            addressExampleCriteria.andUserIdEqualTo(userId).andIsDefaultEqualTo(true);
//        } else {
//            //查询用户选择的地址
//            addressExampleCriteria.andIdEqualTo(addressId);
        }
        List<Address> addresses = addressMapper.selectByExample(addressExample);
        //设置checkedAddress字段
        cartCheckOutForReturn.setCheckedAddress(addresses.get(0));
        //根据cartId到cart表中查询对应的goods_id
        //如果cartId=0，那么就是当前user_id对应的所有checked为1的goods_id的集合
        CartExample cartExample = new CartExample();
        CartExample.Criteria cartExampleCriteria = cartExample.createCriteria();
        cartExampleCriteria.andDeletedEqualTo(false).andCheckedEqualTo(true);
        if(cartId <= 0){
            //查询所有goods_id的集合
            cartExampleCriteria.andUserIdEqualTo(userId);
        } else {
            //查询指定goods_id
            cartExampleCriteria.andIdEqualTo(cartId);
        }
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        //遍历carts，由其中的goods_id去goods表中查信息，并计算actualPrice
        BigDecimal actualPrice = BigDecimal.valueOf(0);
        BigDecimal goodsTotalPrice = BigDecimal.valueOf(0);
        BigDecimal couponPrice = BigDecimal.valueOf(0);
        BigDecimal grouponPrice = BigDecimal.valueOf(0);
        BigDecimal freightPrice = BigDecimal.valueOf(0);
        List<Goods> checkedGoodsList = new ArrayList<>();
        for (Cart cart : carts) {
            Integer goodsId = cart.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            BigDecimal retailPrice = goods.getRetailPrice();
            Short number = cart.getNumber();
            BigDecimal number1 = BigDecimal.valueOf(number);
            BigDecimal multiply = retailPrice.multiply(number1);
            goodsTotalPrice = goodsTotalPrice.add(multiply);
            //根据grouponRulesId查groupon_rules表中的对应goods_id的discount
            //如果grouponRulesId=0，代表没有团购
            if(grouponRulesId > 0){
                //有团购优惠
                Groupon_rules grouponRules = grouponRulesMapper.selectByPrimaryKey(grouponRulesId);
                BigDecimal discount = grouponRules.getDiscount();
                grouponPrice = grouponPrice.add(discount);
            }
            checkedGoodsList.add(goods);
        }
        //设置checkedGoodsList和goodsTotalPrice、couponPrice
        cartCheckOutForReturn.setCheckedGoodsList(checkedGoodsList);
        cartCheckOutForReturn.setGoodsTotalPrice(goodsTotalPrice);
        cartCheckOutForReturn.setGrouponPrice(grouponPrice);
        //根据couponId查coupon表中的discount，但是要判断有没有满足满减条件
        //如果couponId=0或者-1，代表没有优惠券
        if(couponId <= 0){
            //无优惠券优惠
            couponPrice = BigDecimal.valueOf(0);
        } else {
            //有优惠券优惠
            Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
            Short days = coupon.getDays();
            cartCheckOutForReturn.setAvailableCouponLength((int) days);
            //判断是否满足满减条件
            if(goodsTotalPrice.doubleValue() >= coupon.getLimit()){
                couponPrice = coupon.getDiscount();
            } else {
                couponPrice = BigDecimal.valueOf(0);
            }
        }
        //设置couponPrice
        cartCheckOutForReturn.setCouponPrice(couponPrice);
        //判断运费
        if(goodsTotalPrice.doubleValue() >= hasFreightPrice){
            //包邮
            freightPrice = BigDecimal.valueOf(0);
        } else {
            //10元邮费
            freightPrice = BigDecimal.valueOf(10);
        }
        cartCheckOutForReturn.setFreightPrice(freightPrice);
        //计算orderTotalPrice和actualPrice
        BigDecimal orderTotalPrice = goodsTotalPrice.add(freightPrice).subtract(couponPrice);
        //这个没考虑积分减免
        actualPrice = orderTotalPrice;
        cartCheckOutForReturn.setOrderTotalPrice(orderTotalPrice);
        cartCheckOutForReturn.setActualPrice(actualPrice);
        return cartCheckOutForReturn;
    }

    private int updateAfterFastAdd (WxAddCart addGoods,int userId){

            Integer productId = addGoods.getProductId();
            Short number = addGoods.getNumber();
            //获得goodsId
            Integer goodsId = addGoods.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);


            GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(productId);
            String[] specifications = goodsProduct.getSpecifications();
            String str = Arrays.toString(specifications);
            Date date = new Date();
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
            List<Cart> carts = cartMapper.selectByExample(cartExample);
            if (carts == null || carts.size() == 0) {
                Cart cart = new Cart(userId,
                        goodsId,
                        goods.getGoodsSn(),
                        goods.getName(),
                        productId,
                        goods.getRetailPrice(),
                        number,
                        str,
                        true,
                        goods.getPicUrl(),
                        date,
                        false);
                cartMapper.insert(cart);
            } else {
                // 如果返回会自动在添加在购物车中
                Cart cart = new Cart();
                Date date1 = new Date();
                cart.setUpdateTime(date1);
                cart.setNumber(number);
                CartExample cartExample1 = new CartExample();
                cartExample1.createCriteria().andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
                cartMapper.updateByExampleSelective(cart, cartExample1);
            }
            int cartId = cartMapper.selectCartId(productId);

            return cartId;
        }
}


