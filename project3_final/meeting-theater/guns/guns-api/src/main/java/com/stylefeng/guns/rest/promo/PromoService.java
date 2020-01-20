package com.stylefeng.guns.rest.promo;

import com.stylefeng.guns.rest.promo.vo.PromoInfo;
import com.stylefeng.guns.rest.promo.vo.PromoListCondition;

import java.util.List;

public interface PromoService {

    public List<PromoInfo> getPromoInfo(String areaId);

    public boolean createSecOrder(String promoId,String amount,Integer userId);

    public boolean cacheWarming();

    public Boolean savePromoOrderInTransaction(String promoId,String amount,Integer userId);

    public String initPromoStockLog(Integer promoId, Integer amount);

    public PromoInfo getPromoInfoBypromoId(Integer promoId);

    //生成秒杀令牌接口
    public String generatePromoToken(String userId,Integer promoId);
}
