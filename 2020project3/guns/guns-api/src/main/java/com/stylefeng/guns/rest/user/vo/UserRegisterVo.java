package com.stylefeng.guns.rest.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterVo implements Serializable {

    private static final long serialVersionUID = -3585536744493792636L;

    String username;

    String password;

    String email;

    String mobile;

    String address;
}
