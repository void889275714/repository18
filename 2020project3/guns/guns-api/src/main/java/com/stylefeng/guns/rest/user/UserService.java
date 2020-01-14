package com.stylefeng.guns.rest.user;

import com.stylefeng.guns.rest.user.vo.UserInfoVo;
import com.stylefeng.guns.rest.user.vo.UserRegisterVo;

public interface UserService {
    Boolean queryUserNameIsExist(String username);

    int addUser(UserRegisterVo userRegisterVo);

    int modifyUserInformation(UserInfoVo userInfoVo);

    Boolean validate(String userName, String password);

    UserInfoVo getUserInfo(String username);

    Integer queryUserId(String username);
}
