package com.example.app.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fururur
 * @create 2019-07-23-10:19
 */
@Data
@NoArgsConstructor
public class RestResult {
    private int code = ResultCode.SUCCESS.getCode();
    private String msg = ResultCode.SUCCESS.getMsg();
    private String taskId;
    private Object data;

    public RestResult(Object data) {
        this.data = data;
    }

    public RestResult(int code, Object data) {
        this.code = code;
        this.msg = ResultCode.toMsg(code);
        this.data = data;
    }

    public RestResult(int code, String taskId, Object data) {
        this(code, data);
        this.taskId = taskId;
    }
}
