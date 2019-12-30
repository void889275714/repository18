package com.cskaoyan.bean.configmall;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreateGrMsg {
    private int goodsId;
    private BigDecimal discount;
    private int discountMember;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expireTime;
}
