package com.stylefeng.guns.rest.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 1574476043956034438L;

    private Integer uuid;

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private Integer sex;

    private String birthday;

    private Integer lifeState;

    private String biography;

    private String address;

    private Date updateTime;

    private String headAddress;
}
