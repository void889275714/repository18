package com.cskaoyan.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GoodsAllMsg {

    private Integer amount;
    private Integer orders;
   @JsonFormat(pattern = "yyyy-MM-dd")
    private Date day;
    private Integer products;


}
