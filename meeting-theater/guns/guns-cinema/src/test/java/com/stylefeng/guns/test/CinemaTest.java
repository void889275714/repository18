package com.stylefeng.guns.test;

import com.stylefeng.guns.rest.GunsCinemaApplication;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.vo.CinemaBrandAreaHall;
import com.stylefeng.guns.rest.cinema.vo.FieldInfoVO;
import com.stylefeng.guns.rest.modular.cinema.CinemaServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GunsCinemaApplication.class, CinemaServiceImpl.class})
public class CinemaTest {

    @Autowired
    private CinemaService cinemaService;

    @Test
    public void test1(){
        FieldInfoVO fieldInfo = cinemaService.getFieldInfo("1", "1");
        System.out.println(fieldInfo);

    }

    /**
     * 接口2
     */
    @Test
    public void test2(){
        CinemaBrandAreaHall cinemaCondition = cinemaService.getCinemaCondition(99, "99", 99);
        System.out.println(cinemaCondition);
    }

    /**
     * 接口3
     */
    @Test
    public void test3(){
        Map<String, Object> cinemaField = cinemaService.getCinemaField("1");
        System.out.println(cinemaField);
    }

}
