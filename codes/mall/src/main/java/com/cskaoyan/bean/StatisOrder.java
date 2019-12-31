package com.cskaoyan.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 *  "rows": [{
 * 			"amount": 939.00,
 * 			"orders": 2,
 * 			"customers": 1,
 * 			"day": "2019-07-08",
 * 			"pcr": 939.00
 *            }
 *          ]*
 */

@Data
public class StatisOrder {

    Double amount;

    Integer orders;

    Integer customers;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date day;

    Double pcr;

}
