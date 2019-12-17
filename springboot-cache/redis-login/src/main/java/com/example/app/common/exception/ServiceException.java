package com.example.app.common.exception;

import com.example.app.common.entity.ResultCode;
import lombok.Getter;

/**
 * @author Fururur
 * @create 2019-12-16-19:57
 */
@Getter
public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException(int code) {
        super(ResultCode.toMsg(code));
        this.code = code;
    }
}
