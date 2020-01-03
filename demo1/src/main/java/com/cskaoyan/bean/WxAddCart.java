package com.cskaoyan.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bruce
 * @version 1.0.0
 * @date 2020/1/2
 * created by bruce on 2020/1/2 13:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxAddCart {

    Integer goodsId;

    Short number;

    Integer productId;
}
