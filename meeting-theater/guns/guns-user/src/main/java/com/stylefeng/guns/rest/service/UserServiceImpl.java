package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.UserInfoVo;
import com.stylefeng.guns.rest.user.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    MtimeUserTMapper userTMapper;

    /**
     * 查询用户是否存在
     * @param username
     * @return
     */
    @Override
    public Boolean queryUserNameIsExist(String username) {
        Wrapper<MtimeUserT> wrapper = new EntityWrapper();
        wrapper.eq("user_name",username);
        List<MtimeUserT> mtimeUserTS = userTMapper.selectList(wrapper);
        int size = mtimeUserTS.size();
        if (size > 0) {
            return true;
        }
        return false;
    }

    /**
     * 用户注册
     * @param userRegisterVo
     * @return
     */
    @Override
    public int addUser(UserRegisterVo userRegisterVo) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUserName(userRegisterVo.getUsername());
        mtimeUserT.setUserPwd(userRegisterVo.getPassword());
        mtimeUserT.setEmail(userRegisterVo.getEmail());
        mtimeUserT.setUserPhone(userRegisterVo.getMobile());
        mtimeUserT.setAddress(userRegisterVo.getAddress());
        mtimeUserT.setBeginTime(new Date());
        mtimeUserT.setUpdateTime(new Date());
        return userTMapper.insert(mtimeUserT);
    }

    /**
     * 修改用户信息
     * @param userInfoVo
     * @return
     */
    @Override
    public int modifyUserInformation(UserInfoVo userInfoVo) {
        MtimeUserT userT = new MtimeUserT();
        userT.setUuid(userInfoVo.getUuid());
        userT.setUserName(userInfoVo.getUsername());
        userT.setNickName(userInfoVo.getNickname());
        userT.setUserSex(userInfoVo.getSex());
        userT.setBirthday(userInfoVo.getBirthday());
        userT.setEmail(userInfoVo.getEmail());
        userT.setUserPhone(userInfoVo.getPhone());
        userT.setAddress(userInfoVo.getAddress());
        userT.setBiography(userInfoVo.getBiography());
        userT.setLifeState(userInfoVo.getLifeState());
        userT.setUpdateTime(userInfoVo.getUpdateTime());

        Integer integer = userTMapper.updateById(userT);
        return integer;
    }

    /**
     * 验证用户信息
     * @param userName
     * @param password
     * @return
     */
    @Override
    public Boolean validate(String userName, String password) {
        MtimeUserT userT = userTMapper.selectMtimeUserTByUserNameAndPassword(userName,password);
        if (userT!=null) return true;
        return false;
    }

    /**
     * 获得用户信息
     * @param username
     * @return
     */
    @Override
    public UserInfoVo getUserInfo(String username) {
        EntityWrapper<MtimeUserT> wrapper = new EntityWrapper<>();
        wrapper.eq("user_name",username);
        List<MtimeUserT> userTS = userTMapper.selectList(wrapper);
        UserInfoVo userInfoVo = null;
        if (userTS.size()==1) {
            MtimeUserT userT = userTS.get(0);
            userInfoVo = new UserInfoVo();
            userInfoVo.setUuid(userT.getUuid());
            userInfoVo.setUsername(userT.getUserName());
            userInfoVo.setNickname(userT.getNickName());
            userInfoVo.setEmail(userT.getEmail());
            userInfoVo.setPhone(userT.getUserPhone());
            userInfoVo.setSex(userT.getUserSex());
            userInfoVo.setBirthday(userT.getBirthday());
            userInfoVo.setLifeState(userT.getLifeState());
            userInfoVo.setBiography(userT.getBiography());
            userInfoVo.setAddress(userT.getAddress());
            userInfoVo.setUpdateTime(userT.getUpdateTime());
            userInfoVo.setHeadAddress(userT.getHeadUrl());
        }
        return userInfoVo;
    }

    /**
     *  查询用户 id
     * @param username
     * @return
     */
    @Override
    public Integer queryUserId(String username) {
        Integer uuid = userTMapper.selectUuidByUserName(username);
        return uuid;
    }
}
