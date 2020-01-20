package com.stylefeng.guns.rest.modular.auth.controller;

import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.model.Result;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //这里不改成DB的，验证只能用admin   或者修改simpleValidator的方法
    @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "${jwt.auth-path}")
    public Result createAuthenticationToken(AuthRequest authRequest) {

        boolean validate = reqValidator.validate(authRequest);

        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            //如果验证了有这个用户，则生成一个token
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            //我们把生成的token放到 redis里面
            redisTemplate.opsForValue().set(authRequest.getUserName(),token,1800, TimeUnit.SECONDS);

            return Result.ok(new AuthResponse(token, randomKey));
        }
        if (!validate){
            return new Result(1,"用户名或密码错误,请重试！");
        }
        return new Result(999,"系统出现异常，请联系管理员！");

    }
}
