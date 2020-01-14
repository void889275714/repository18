package com.stylefeng.guns.rest.modular.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.model.Result;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.UserInfoVo;
import com.stylefeng.guns.rest.user.vo.UserRegisterVo;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Reference(interfaceClass = UserService.class)
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     * @param userRegisterVo
     * @return
     */
    @RequestMapping("user/register")
    public Result register(UserRegisterVo userRegisterVo) {
        Boolean aBoolean = userService.queryUserNameIsExist(userRegisterVo.getUsername());
        if (aBoolean) {
            return new Result(1,"该用户名已存在，请修改后重试！");
        }
        int add = userService.addUser(userRegisterVo);
        if (add == 1) {
            return Result.ok("注册成功！");
        }
        return new Result(999,"系统出现异常，请联系管理员!");
    }

    /**
     * 用户登出
     * 需携带头信息(Authorization)
     * @param Authorization
     * @return
     */
    @RequestMapping("user/logout")
    public Result logout(@RequestHeader String Authorization) {
        String header = Authorization;
        if (!StringUtil.isNullOrEmpty(header)){
            String authToken = "";
            if (header.startsWith("Bearer ")) {
                authToken = header.substring(7);
                String username = jwtTokenUtil.getUsernameFromToken(authToken);
                Object o = redisTemplate.opsForValue().get(username);
                if (o != null) {
                    Boolean delete = redisTemplate.delete(username);
                    if (delete){
                        return Result.ok("退出成功！");
                    } else {
                        return Result.failure();
                    }
                }
            }
        }
        return new Result(700,"查询失败，用户尚未登录！");
    }

    /**
     * 用户名检测
     * @param username
     * @return
     */
    @RequestMapping("user/check")
    public Result check(String username) {
        Boolean aBoolean = userService.queryUserNameIsExist(username);
        if (aBoolean) {
            return new Result(1,"用户名已经注册");
        }
        if (!aBoolean) {
            return Result.ok("恭喜！用户名未被占用");
        }
        return new Result(999,"系统出现异常，请联系管理员!");
    }

    /**
     * 获取用户详情
     * @param Authorization
     * @return
     */
    @RequestMapping("user/getUserInfo")
    public Result getUserInfo(@RequestHeader String Authorization){
        String header = Authorization;
        if (!StringUtil.isNullOrEmpty(header)) {
            String authToken = null;
            if (header.startsWith("Bearer ")) {
                authToken = header.substring(7);
                String username = jwtTokenUtil.getUsernameFromToken(authToken);
                UserInfoVo userInfo = userService.getUserInfo(username);
                if (userInfo != null) {
                    return Result.ok(userInfo);
                }else {
                    return  Result.failure();
                }
            }
        }
        return new Result(700,"查询失败，用户尚未登录");
    }


    /**
     * 更新用户信息
     * @param userInfoVo
     * @return
     */
    @RequestMapping("user/updateUserInfo")
    public Result updateUserInfo(UserInfoVo userInfoVo) {
        int i = userService.modifyUserInformation(userInfoVo);
        if (i == 1) {
            return Result.ok(userInfoVo);
        }else if (i == 0){
           return new Result(1,"用户信息修改失败！！");
        }else {
            //i的值可以为几？
            return new Result(999,"系统出现异常，请联系管理员！");
        }
    }
}
