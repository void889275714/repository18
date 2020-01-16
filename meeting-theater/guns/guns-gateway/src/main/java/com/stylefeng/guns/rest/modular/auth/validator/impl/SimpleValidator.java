package com.stylefeng.guns.rest.modular.auth.validator.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.modular.auth.validator.dto.Credence;
import com.stylefeng.guns.rest.user.UserService;
import org.springframework.stereotype.Service;

/**
 * 直接验证账号密码是不是admin
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
public class SimpleValidator implements IReqValidator {

    private static String USER_NAME = "admin";

    private static String PASSWORD = "admin";

    //要验证数据库里的名字，先把这个引进来
    @Reference(interfaceClass = UserService.class)
    UserService userService;

    @Override
    public boolean validate(Credence credence) {

        String userName = credence.getCredenceName();
        String password = credence.getCredenceCode();
        Boolean validate = userService.validate(userName, password);
        return validate;

//        if (USER_NAME.equals(userName) && PASSWORD.equals(password)) {
//            return true;
//        } else {
//            return false;
//        }
    }
}
