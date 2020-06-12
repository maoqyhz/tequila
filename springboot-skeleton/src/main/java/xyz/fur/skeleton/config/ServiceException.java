package xyz.fur.skeleton.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.fur.skeleton.entity.base.ResultCode;

/**
 * @author Fururur
 * @date 2020-01-03-10:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {
    private ResultCode code;
    private String msg;

    public ServiceException(ResultCode code) {
        super(code.getMsg());
        this.code = code;
        this.msg = code.getMsg();
    }

    public ServiceException(ResultCode code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}