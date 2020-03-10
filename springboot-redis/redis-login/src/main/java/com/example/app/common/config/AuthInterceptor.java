package com.example.app.common.config;

import com.alibaba.fastjson.JSONObject;
import com.example.app.common.entity.RestResult;
import com.example.app.common.entity.ResultCode;
import com.example.app.utils.SpringUtil;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截器
 *
 * @author Fururur
 * @create 2019-07-23-9:22
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplateJson");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 只对 mvc 中的方法进行拦截
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        boolean hasAuth = false;
        String uri = request.getRequestURI();
        log.info(uri);
        // 指定拦截规则 可用注解代替
        if (uri.startsWith("/auth"))
            hasAuth = true;
        String token = request.getHeader("X-Token");
        if (!Strings.isNullOrEmpty(token)) {
            boolean hasToken = redisTemplate.hasKey("userToken:" + token);
            hasAuth = hasToken;
        }
        if (!hasAuth) {
            log.error("no auth to access {}", uri);
            RestResult r = new RestResult(ResultCode.NO_AUTH.getCode());
            PrintWriter out = null;
            try {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                out = response.getWriter();
                out.println(JSONObject.toJSONString(r));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
        }
        return hasAuth;
    }
}
