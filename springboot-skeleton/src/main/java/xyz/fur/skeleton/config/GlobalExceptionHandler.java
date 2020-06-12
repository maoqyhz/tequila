package xyz.fur.skeleton.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import xyz.fur.skeleton.entity.base.RestResult;

/**
 * 全局异常处理
 *
 * @author Fururur
 * @create 2020-01-02-11:02
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public RestResult serviceExceptionHandler(ServiceException ex) {
        return RestResult.bizError(ex.getCode(), ex.getMsg());
    }

    /**
     * 处理一般500异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public RestResult runtimeExceptionHandler(RuntimeException ex) {
        log.error(ex.toString(), ex);
        return new RestResult() {{
            setCode(500);
            setMsg("服务器异常，请联系管理员");
            setData(ex.toString());
        }};
    }

    /**
     * 处理404异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResult noHandlerFoundExceptionHandler(Exception ex) {
        return new RestResult() {{
            setCode(404);
            setMsg("访问资源不存在或参数错误");
            setData(ex.toString());
        }};
    }

    /**
     * 处理405异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestResult httpRequestMethodNotSupportedExceptionHandler(Exception ex) {
        return new RestResult() {{
            setCode(405);
            setMsg("HTTP请求方式错误");
            setData(ex.toString());
        }};
    }
}
