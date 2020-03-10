package com.example.app;

import com.example.app.common.entity.ResultCode;
import com.example.app.common.exception.ServiceException;
import com.example.app.utils.TokenUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Fururur
 * @create 2019-12-13-17:20
 */
@Slf4j
@Transactional
@Service
public class IUserServiceImpl implements IUserService {
    private final IUserRepository repository;
    private final StringRedisTemplate stringRedisTemplate;

    @Resource(name = "redisTemplateJson")
    private HashOperations<String, String, Object> hashOperations;

    @Resource(name = "redisTemplateJson")
    private RedisTemplate redisTemplate;


    public IUserServiceImpl(IUserRepository repository, StringRedisTemplate stringRedisTemplate) {
        this.repository = repository;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String checkLogin(String username, String password) {
        User u = repository.findUserByUsername(username);
        if (null == u)
            throw new ServiceException(ResultCode.LOGIN_NO_USER.getCode());
        String pwd = TokenUtils.md5(password, u.getSalt());
        // 登录成功 颁发token
        if (pwd.equals(u.getPassword())) {
            String token = TokenUtils.generateToken(u.getSalt());

            String key = String.format("userToken:%s:%s", token, u.getId());
            hashOperations.put(key, "id", u.getId());
            hashOperations.put("userToken:" + token, "username", u.getUsername());
            redisTemplate.expire("userToken:" + token, 30, TimeUnit.DAYS);
            // stringRedisTemplate.opsForValue().set(token, username, 30, TimeUnit.DAYS);
            return token;
        } else {
            // 账户存在，密码错误
            // 2分钟内登录失败5次，1小时内禁止登录
            String loginTimes = stringRedisTemplate.opsForValue().get("loginTimes:" + username);
            if (Strings.isNullOrEmpty(loginTimes)) {
                stringRedisTemplate.opsForValue().set("loginTimes:" + username, "1", 2, TimeUnit.MINUTES);
            } else {
                int count = Integer.valueOf(loginTimes);
                if (count < 4) {
                    stringRedisTemplate.opsForValue().increment("loginTimes:" + username);
                } else if (count == 4) {
                    stringRedisTemplate.opsForValue().increment("loginTimes:" + username);
                    stringRedisTemplate.expire("loginTimes:" + username, 1, TimeUnit.HOURS);
                } else {
                    throw new ServiceException(ResultCode.LOGIN_PASSWORD_ERROR_TOO_MANY_TIMES.getCode());
                }
            }
            throw new ServiceException(ResultCode.LOGIN_PASSWORD_ERROR.getCode());
        }
    }

    @Override
    public String logout(String username) {
        // todo
        // 可实现缓存剔除
        return null;
    }
}
