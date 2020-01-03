package com.cskaoyan.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RowsMsg {

    @JsonFormat(pattern = "yyyy-MM-dd ")
    private Date day;
    private Integer users;
}
