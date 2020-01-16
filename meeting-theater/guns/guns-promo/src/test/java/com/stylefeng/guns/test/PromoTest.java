package com.stylefeng.guns.test;


import com.stylefeng.guns.rest.GunsPromoApplication;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.CinemaBrandAreaHall;
import com.stylefeng.guns.rest.cinema.vo.FieldInfoVO;

import com.stylefeng.guns.rest.modular.promo.PromoServiceImpl;
import com.stylefeng.guns.rest.promo.PromoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GunsPromoApplication.class, PromoServiceImpl.class})
public class PromoTest {

    @Autowired
    private PromoService promoService;

//    @Test
//    public void test1(){
//        boolean secOrder = promoService.createSecOrder("1", "5");
//        if (secOrder) {
//            System.out.println("redis订单异步处理成功！");
//        }
//
//    }
//
//    /**
//     * 接口2
//     */
//    @Test
//    public void test2(){
//        boolean b = promoService.cacheWarming();
//        if (b) {
//            System.out.println("redis预热成功");
//        }
//    }

}
