package com.stylefeng.guns.rest.modular.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.util.concurrent.RateLimiter;
import com.mysql.cj.util.StringUtils;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import com.stylefeng.guns.rest.ResponseVO;
import com.stylefeng.guns.rest.consistant.RedisPrefixConsistant;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.promo.PromoService;
import com.stylefeng.guns.rest.promo.vo.PromoCreate;
import com.stylefeng.guns.rest.promo.vo.PromoInfo;
import com.stylefeng.guns.rest.promo.vo.PromoListCondition;
import com.stylefeng.guns.rest.promo.vo.PromoStatus;
import com.stylefeng.guns.rest.user.UserService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import sun.management.snmp.util.MibLogger;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@RestController
public class PromoController {

    @Reference(interfaceClass = PromoService.class, check = false)
    private PromoService promoService;

    private ExecutorService executorService;

    //令牌桶工具包
    private RateLimiter rateLimiter;

//    @PostConstruct
//    public void init(){
//        executorService = Executors.newFixedThreadPool(100);
//
//        //如果是单机的，那么就没有本地或者分布式的问题
//
//        /**如果是一个集群的，假设现在gateway有两个节点 200
//            而是需要多少就给多少  Nginx 120次请求  80次 能够按需分配
//            不足之处，依赖于 Redis 服务的稳定性
//         网关服务保护了秒杀服务
//
//         */
//        //每秒生成10个令牌   限制速率
//        rateLimiter = RateLimiter.create(100);
//
//
//    }




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

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 接口2  秒杀下单   需登录
     * @param
     * @return
     */
    @RequestMapping(value = "promo/createOrder",method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO postCreateInfo(@RequestParam(required = true,name = "promoId") Integer promoId,
                                     @RequestParam(required = true,name = "amount") Integer amount,
                                     @RequestHeader String Authorization,@RequestParam(required = true,name = "promoToken") String promoToken ){
        ResponseVO responseVO = new ResponseVO();

        //获取一个令牌, 返回的是一个等待时间的比值
        //double acquire = rateLimiter.acquire();

        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        Integer userId = userService.queryUserId(username);

        //判断秒杀令牌是否合法
        String promoTokenKey = String.format(RedisPrefixConsistant.USER_PROMO_TOKEN_PREFIX2,promoId,userId);
        String tokenInRedis = (String) redisTemplate.opsForValue().get(promoTokenKey);
        if (StringUtil.isNullOrEmpty(tokenInRedis)) {
            responseVO.setMsg("获取缓存中秒杀令牌失败!");
            responseVO.setStatus(999);
            return responseVO;
        }
        if (!tokenInRedis.equals(promoToken)) {
            responseVO.setMsg("秒杀令牌参数非法!");
            responseVO.setStatus(999);
            return responseVO;
        }


        //利用线程池排队 形成一个阻塞窗口
        // 实现一个匿名接口
//        Future future = executorService.submit(() -> {
//            //写初始化流水的逻辑
//            String stockLogId = promoService.initPromoStockLog(Integer.valueOf(promoId), Integer.valueOf(amount));
//            if (StringUtil.isNullOrEmpty(stockLogId)) {
//                    throw new GunsException(GunsExceptionEnum.CREATE_ORDER_ERROR);
//            }
//
//
//        });


        boolean secOrder = promoService.createSecOrder(String.valueOf(promoId),String.valueOf(amount),userId);
        if (secOrder) {
            responseVO.setStatus(0);
            responseVO.setMsg("下单成功");
        }else {
            throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
        }

//        try {
//            future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            responseVO.setStatus(999);
//            responseVO.setMsg("下单失败");
//            return responseVO;
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//            responseVO.setStatus(999);
//            responseVO.setMsg("下单失败");
//            return responseVO;
//        } catch (GunsException e) {
//            e.printStackTrace();
//            responseVO.setStatus(999);
//            responseVO.setMsg("下单失败");
//            return responseVO;
//        }

        responseVO.setStatus(0);
        responseVO.setMsg("下单成功");
        return responseVO;
    }



    /*--------------------------4, 获取秒杀令牌接口  ----------------------------*/

    /**
     * 获得令牌
     * 最终返回一个秒杀令牌
     */
    @RequestMapping("promo/generateToken")
    public ResponseVO generateToken(@RequestParam(required = true) Integer promoId, HttpServletRequest request, HttpServletResponse response){
        ResponseVO responseVO = new ResponseVO();
        //获取 userId
        String requestHeader = request.getHeader("Authorization");
        String substring = requestHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(substring);
        Integer userId = userService.queryUserId(username);


        //判断活动状态
        PromoInfo promoInfoBypromoId = promoService.getPromoInfoBypromoId(Integer.valueOf(promoId));
        if (promoInfoBypromoId == null) {
            responseVO.setMsg("promoId不合法");
            responseVO.setStatus(999);
            return responseVO;
        }
        Integer status = promoInfoBypromoId.getStatus();
        if (PromoStatus.ING.getIndex() != status){
            responseVO.setMsg("活动未开始");
            responseVO.setStatus(999);
            return responseVO;
        }


        //判断库存是否售罄
        String soldKey = RedisPrefixConsistant.PROMO_STOCK_NULL_PROMOID + promoId;
        Object o = redisTemplate.opsForValue().get(soldKey);
        if (o != null) {
            log.info("库存已经售罄，promoId:{}",promoId);
            responseVO.setMsg("下单失败！");
            responseVO.setStatus(1);
            return responseVO;
        }



        String token = promoService.generatePromoToken(String.valueOf(userId), promoId);
        //注意这里的token 是 uuid
        if (StringUtil.isNullOrEmpty(token)){
            responseVO.setMsg("获取秒杀令牌失败");
            responseVO.setStatus(999);
            return responseVO;
        }


        responseVO.setMsg(token);
        responseVO.setStatus(0);
        return responseVO;
    }

}
