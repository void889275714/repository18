package com.stylefeng.guns.rest.modular.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.ResponseVO;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.promo.PromoService;
import com.stylefeng.guns.rest.promo.vo.PromoCreate;
import com.stylefeng.guns.rest.promo.vo.PromoInfo;
import com.stylefeng.guns.rest.promo.vo.PromoListCondition;
import com.stylefeng.guns.rest.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PromoController {

    @Reference(interfaceClass = PromoService.class, check = false)
    private PromoService promoService;


    /**
     * 接口1  根据影院id查询秒杀订单列表
     * 坑爹的前端
     * @param promoListCondition
     * @return
     */
    @RequestMapping("promo/getPromo")
    public ResponseVO getPromoInfo(PromoListCondition promoListCondition){
        ResponseVO responseVO = new ResponseVO();
        List<PromoInfo> data = promoService.getPromoInfo(promoListCondition.getAreaId());
        responseVO.setData(data);
        responseVO.setImgPre("");
        responseVO.setMsg("");
        responseVO.setNowPage(promoListCondition.getNowPage());
        responseVO.setStatus(0);
        responseVO.setTotalPage("");
        return responseVO;
    }

    /*--------------------------3, 将库存信息发布到缓存----------------------------*/

    /**
     * 接口3 将库存信息发布到缓存
     * @return
     */
    @RequestMapping("promo/publishPromoStock")
    public ResponseVO getPublish(){
        ResponseVO responseVO = new ResponseVO();
        //先去promo_stock库里拿到 promoId 和 stock吧
        boolean result = promoService.cacheWarming();
        if (result) {
            responseVO.setMsg("发布成功！");
            responseVO.setStatus(0);
            return responseVO;
        }
        responseVO.setMsg("缓存预热失败！");
        return responseVO;
    }

    /*--------------------------2, 秒杀下单接口 创建订单（需要先登录）----------------------------*/


    @Reference(interfaceClass = UserService.class)
    private UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 接口2  秒杀下单   需登录
     * @param promoCreate
     * @return
     */
    @RequestMapping("promo/createOrder")
    public ResponseVO postCreateInfo(@RequestBody PromoCreate promoCreate, @RequestHeader String Authorization ){
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        Integer userId = userService.queryUserId(username);

        ResponseVO responseVO = new ResponseVO();
        boolean secOrder = promoService.createSecOrder(promoCreate.getPromoId(), promoCreate.getAmount(),userId);
        if (secOrder) {
            responseVO.setStatus(0);
            responseVO.setMsg("下单成功");
        }
        return responseVO;
    }




    /*--------------------------4, 获取秒杀令牌接口  ----------------------------*/

    /**
     *
     */
    @RequestMapping("promo/generateToken")
    public String generateToken(){
        return "{\n" +
                "\t\"data\":\"\",\n" +
                "\t\"imgPre\":\"\",\n" +
                "\t\"msg\":\"be3351baf2b94e19bbc90ed76093bec9\",\n" +
                "\t\"nowPage\":\"\",\n" +
                "\t\"status\":0,\n" +
                "\t\"totalPage\":\"\"\n" +
                "}";
    }

}
