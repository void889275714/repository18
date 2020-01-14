package com.stylefeng.guns.rest.modular.promo;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoOrderMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromo;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoOrder;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoStock;
import com.stylefeng.guns.rest.modular.mq.MQproducer;
import com.stylefeng.guns.rest.promo.PromoService;
import com.stylefeng.guns.rest.promo.vo.PromoInfo;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Service(interfaceClass = PromoService.class)
public class PromoServiceImpl implements PromoService {

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimePromoMapper mtimePromoMapper;

    @Autowired
    MtimePromoStockMapper mtimePromoStockMapper;


    /**
     * 根据影院id查询秒杀订单列表
     * 影院id 当不传入的时候查询所有的秒杀活动列表
     * @return
     */
    @Override
    public List<PromoInfo> getPromoInfo(String areaId) {
        List<PromoInfo> promoInfoList = new ArrayList<>();
        if (!"".equals(areaId) && !"99".equals(areaId)) {
            //因为areaId不为空，则先去 cinema表中查
            EntityWrapper<MtimeCinemaT> mtimeCinemaTEntityWrapper = new EntityWrapper<>();
            mtimeCinemaTEntityWrapper.eq("area_id",areaId);
            List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectList(mtimeCinemaTEntityWrapper);
            //因为 areaId 也是唯一的，所以只要拿到影院的uuid即 cinemaId 即可
            MtimeCinemaT mtimeCinemaT = mtimeCinemaTS.get(0);
            Integer cinemaIdT = mtimeCinemaT.getUuid();
            String cinemaAddress = mtimeCinemaT.getCinemaAddress();
            String cinemaName = mtimeCinemaT.getCinemaName();
            String imgAddress = mtimeCinemaT.getImgAddress();

            //这个时候去promo表中查，通过cinemaIdT
            EntityWrapper<MtimePromo> proEntityWrapper = new EntityWrapper<>();
            proEntityWrapper.eq("cinema_id",cinemaIdT);
            List<MtimePromo> mtimePromos = mtimePromoMapper.selectList(proEntityWrapper);
            //进行数据封装
            for (MtimePromo mtimePromo : mtimePromos) {
                PromoInfo promoInfo = new PromoInfo();
                promoInfo.setCinemaAddress(cinemaAddress);
                promoInfo.setCinemaId(String.valueOf(cinemaIdT));
                promoInfo.setCinemaName(cinemaName);
                promoInfo.setDescription(mtimePromo.getDescription());
                //设置时间格式
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format1 = simpleDateFormat.format(mtimePromo.getEndTime());
                promoInfo.setEndTime(format1);

                promoInfo.setImgAddress(imgAddress);
                promoInfo.setPrice(mtimePromo.getPrice());
                //设置时间格式
                String format = simpleDateFormat.format(mtimePromo.getStartTime());
                promoInfo.setStartTime(format);
                promoInfo.setStatus(mtimePromo.getStatus());
                //去stock表中查询库存，根据 mtimePromo的UUID
                EntityWrapper<MtimePromoStock> promoStockEntityWrapper = new EntityWrapper<>();
                promoStockEntityWrapper.eq("promo_id",mtimePromo.getUuid());
                List<MtimePromoStock> mtimePromoStocks = mtimePromoStockMapper.selectList(promoStockEntityWrapper);
                Integer stock = mtimePromoStocks.get(0).getStock();
                promoInfo.setStock(stock);
                promoInfo.setUuid(mtimePromo.getUuid());
                promoInfoList.add(promoInfo);
            }

        } else {
        //如果 areaId 为空的话就先查promo表，全显示了
        EntityWrapper<MtimePromo> promoEntityWrapper = new EntityWrapper<>();
        List<MtimePromo> mtimePromos = mtimePromoMapper.selectList(promoEntityWrapper);
        int size = mtimePromos.size();

        //封装数据
        for (int i = 0; i < size; i++) {
            PromoInfo promoInfo = new PromoInfo();
            MtimePromo mtimePromo = mtimePromos.get(i);
            //拿到 cinemaId 去 cinema表中查信息
            Integer cinemaId = mtimePromo.getCinemaId();
            EntityWrapper<MtimeCinemaT> cinemaTEntityWrapper = new EntityWrapper<>();
            cinemaTEntityWrapper.eq("UUID",cinemaId);
            List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectList(cinemaTEntityWrapper);
            MtimeCinemaT mtimeCinemaT = mtimeCinemaTS.get(0);
            // 1  11  cinemaAddress
            promoInfo.setCinemaAddress(mtimeCinemaT.getCinemaAddress());
            // 2  11  cinemaId
            promoInfo.setCinemaId(String.valueOf(cinemaId));
            // 3  11  cinemaName
            promoInfo.setCinemaName(mtimeCinemaT.getCinemaName());
            // 4  11 description
            promoInfo.setDescription(mtimePromo.getDescription());
            // 5  11 endTime
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format1 = simpleDateFormat.format(mtimePromo.getEndTime());
            promoInfo.setEndTime(format1);
            // 6  11 imgAddress
            promoInfo.setImgAddress(mtimeCinemaT.getImgAddress());
            // 7  11 price
            promoInfo.setPrice(mtimePromo.getPrice());
            // 8  11 startTime
            String format = simpleDateFormat.format(mtimePromo.getStartTime());
            promoInfo.setStartTime(format);
            // 9  11 status
            promoInfo.setStatus(mtimePromo.getStatus());
            // 10  11 stock
            EntityWrapper<MtimePromoStock> promoStockEntityWrapper = new EntityWrapper<>();
            promoStockEntityWrapper.eq("promo_id",mtimePromo.getUuid());
            List<MtimePromoStock> mtimePromoStocks = mtimePromoStockMapper.selectList(promoStockEntityWrapper);
            Integer stock = mtimePromoStocks.get(0).getStock();
            promoInfo.setStock(stock);
            //11 11  uuid
            promoInfo.setUuid(mtimePromo.getUuid());

            promoInfoList.add(promoInfo);
        }

    }
        return promoInfoList;
    }





    /*----------------------------接口2  秒杀下单-----------------------------------------*/

    @Autowired
    MtimePromoOrderMapper mtimePromoOrderMapper;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    MQproducer mQproducer;


    /**
     * 下单成功返回true
     * @param promoId
     * @param amount
     * @return
     */
    @Override
    public boolean createSecOrder(String promoId, String amount,Integer userId) {
        Integer amountInt = Integer.valueOf(amount);
        if (amountInt > 5) {
            return false;
        }
        //这个时候我们肯定能拿到UserId  JWTUtil.getToken 惯例，我们先使用 userId = 1

        //先判断有没有库存给下单
//        EntityWrapper<MtimePromoStock> promoStockEntityWrapper = new EntityWrapper<>();
//        promoStockEntityWrapper.eq("promo_id",promoId);
//        List<MtimePromoStock> mtimePromoStocks = mtimePromoStockMapper.selectList(promoStockEntityWrapper);
//        Integer stock = mtimePromoStocks.get(0).getStock();

        Integer stock = (Integer) redisTemplate.opsForValue().get("promo_id_" + promoId);
        if ((stock - amountInt) < 0) {
            return false;
        }
//        //更新库存
//        MtimePromoStock mtimePromoStock = new MtimePromoStock();
//        mtimePromoStock.setStock(stock-amountInt);
//        Integer update = mtimePromoStockMapper.update(mtimePromoStock, promoStockEntityWrapper);
        //库存是有的，那么开始插入新的数据吧
        MtimePromoOrder mtimePromoOrder = new MtimePromoOrder();
        mtimePromoOrder.setUuid(null);

        //这里要拿到userId
        mtimePromoOrder.setUserId(userId);
        //通过 promoId  拿到 cinemaId
        EntityWrapper<MtimePromo> promoEntityWrapper = new EntityWrapper<>();
        promoEntityWrapper.eq("uuid",promoId);
        List<MtimePromo> mtimePromos = mtimePromoMapper.selectList(promoEntityWrapper);
        MtimePromo mtimePromo = mtimePromos.get(0);
        Integer cinemaId = mtimePromo.getCinemaId();
        mtimePromoOrder.setCinemaId(cinemaId);
        //设置 EXchange_code
        String exchangeCode = "EX" + System.currentTimeMillis()
                + (long) (Math.random() * 10000000L);
        mtimePromoOrder.setExchangeCode(exchangeCode);
        mtimePromoOrder.setAmount(amountInt);
        mtimePromoOrder.setPrice(mtimePromo.getPrice());
        mtimePromoOrder.setStartTime(mtimePromo.getStartTime());
        mtimePromoOrder.setCreateTime(new Date());
        mtimePromoOrder.setEndTime(mtimePromo.getEndTime());

        Integer insert = mtimePromoOrderMapper.insert(mtimePromoOrder);
        //扣减 redis中的库存
        String key = "promo_id_" + promoId;
        Long increment = redisTemplate.opsForValue().increment(key, amountInt * -1);

        //就调用生产消息的方法
        Boolean aBoolean = null;
        try {
            aBoolean = mQproducer.sendMessageDecreaseStock(Integer.valueOf(promoId), amountInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
        if (aBoolean) {
            System.out.println("发送消息成功，数据库更新成功！");
        }

        if ((insert > 0) && (increment > 0)){
            return true;
        }
        return false;
    }


    /*----------------------------接口3  缓存预热-----------------------------------------*/


    /**
     * /promo/generateToken
     * @return
     */
    @Override
    public boolean cacheWarming() {
        EntityWrapper<MtimePromoStock> mtimePromoStockEntityWrapper = new EntityWrapper<>();
        List<MtimePromoStock> mtimePromoStocks = mtimePromoStockMapper.selectList(mtimePromoStockEntityWrapper);
        int count = 0;
        for (MtimePromoStock mtimePromoStock : mtimePromoStocks) {
            redisTemplate.opsForValue().set("promo_id_"+ mtimePromoStock.getPromoId(),mtimePromoStock.getStock());
            count++;
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

}
