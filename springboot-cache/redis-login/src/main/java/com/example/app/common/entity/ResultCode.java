package com.example.app.common.entity;

import lombok.Getter;

import java.util.Optional;

/**
 * 错误代码定义
 *
 * @author Fururur
 * @create 2019-07-23-10:24
 */
@Getter
public enum ResultCode {
    SUCCESS(0, "Success"),
    LOGIN_SUCCESS(2001, "登录成功"),

    NO_AUTH(-40003, "无访问权限"),
    REQUEST_FAILED_ERROR(-5001, "后台请求返回失败"),
    LOGIN_NO_USER(-5002, "用户不存在"),
    LOGIN_PASSWORD_ERROR(-5003, "用户名或密码错误"),
    LOGIN_PASSWORD_ERROR_TOO_MANY_TIMES(-5004, "2分钟内登录失败5次，1小时内禁止登录");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Optional<ResultCode> getByCode(int code) {
        for (ResultCode ec : ResultCode.values()) {
            if (ec.getCode() == code) {
                return Optional.of(ec);
            }
        }
        return Optional.empty();
    }

    public static String toMsg(int code) {
        Optional<ResultCode> rc = getByCode(code);
        return rc.map(ResultCode::getMsg).orElse(null);
    }
}
