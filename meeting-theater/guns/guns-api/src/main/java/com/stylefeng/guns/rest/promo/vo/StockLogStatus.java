package com.stylefeng.guns.rest.promo.vo;


import lombok.Data;
import org.apache.ibatis.javassist.bytecode.SignatureAttribute;

import java.io.Serializable;
@Data
public class StockLogStatus implements Serializable {

    private Integer index;

    private String description;

    private static final long serialVersionUID = 4877594756681331032L;

   public static StockLogStatus INIT = new StockLogStatus(1,"初始化");
   public static StockLogStatus SUCCESS = new StockLogStatus(2,"成功");
   public static StockLogStatus FAIL = new StockLogStatus(3,"失败");


    public StockLogStatus() {
    }

    public StockLogStatus(Integer index, String description) {
        this.index = index;
        this.description = description;
    }
}
