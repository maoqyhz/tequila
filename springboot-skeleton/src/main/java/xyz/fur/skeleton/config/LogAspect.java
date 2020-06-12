package xyz.fur.skeleton.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *
 * @author Fururur
 * @create 2019-07-23-13:54
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(public * xyz.fur.skeleton.controller..*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        String params;
        try {
            params = JSONObject.toJSONString(arguments);
        } catch (Exception e) {
            params = Arrays.toString(arguments);
        }
        log.info("Request To     : {}: {}", request.getMethod(), request.getRequestURI());
        if (params.length() < 200) {
            log.info("Request Args   : {}", params);
        } else {
            log.info("Request Args   : {}", params.substring(0, 200));
        }
    }
}
