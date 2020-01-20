package com.stylefeng.guns.rest.consistant;

public class RedisPrefixConsistant {

    //库存售罄缓存  Key 前缀
    public static String PROMO_STOCK_NULL_PROMOID = "promo_stock_stock_null_promoid_";

    //秒杀令牌token存放缓存前缀
    public static String USER_PROMO_TOKEN_PREFIX = "user_promo_token_prefix_";


    //秒杀令牌token存放缓存前缀2
    public static String USER_PROMO_TOKEN_PREFIX2 = "user_promo_token_prefix_promoId_%s_userId_%s";

    //秒杀令牌数量限制缓存前缀
    public static String PROMO_STOCK_AMOUNT_LIMIT = "promo_stock_amount_limit_";
}
