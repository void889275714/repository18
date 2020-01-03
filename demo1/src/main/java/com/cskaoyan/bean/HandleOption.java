package com.cskaoyan.bean;

import lombok.Data;

/**
 * @Description TODO
 * @Date 2020-01-01 21:22
 * @Created by ouyangfan
 */
@Data
public class HandleOption {
    boolean cancel;
    boolean delete;
    boolean pay;
    boolean comment;
    boolean confirm;
    boolean refund;
    boolean rebuy;

    public HandleOption() {
        this.cancel = false;
        this.delete = false;
        this.pay = false;
        this.comment = false;
        this.confirm = false;
        this.refund = false;
        this.rebuy = false;
    }
}
