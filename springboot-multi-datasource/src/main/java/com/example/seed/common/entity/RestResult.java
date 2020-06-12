package com.example.seed.common.entity;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

/**
 * @author Fururur
 * @date 2019-07-23-10:19
 */
@Data
@NoArgsConstructor
public class RestResult {
    private int code = ResultCode.SUCCESS.getCode();
    private String msg = ResultCode.SUCCESS.getMsg();
    private Object data;

    public RestResult(Object data) {
        this.data = data;
    }

    public RestResult(ResultCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public RestResult(ResultCode code, Object data) {
        this(code);
        this.data = data;
    }

    public static RestResult ok() {
        return new RestResult();
    }

    public static RestResult ok(Object data) {
        return new RestResult(data);
    }

    public static RestResult bizError(ResultCode code) {
        return new RestResult(code);
    }

    public static RestResult bizError(ResultCode code, String msg) {
        return new RestResult() {{
            setCode(code.getCode());
            setMsg(msg);
        }};
    }

    public static RestResult fail() {
        return new RestResult(ResultCode.REQUEST_FAILED_ERROR);
    }

    public static RestResult invalidParams(BindingResult bindingResult) {
        Multimap<String, String> errors = ArrayListMultimap.create();
        bindingResult.getFieldErrors().forEach(e -> errors.put("errors", e.getDefaultMessage()));
        return new RestResult() {{
            setCode(ResultCode.INVALID_PARAMS_ERROR.getCode());
            setMsg(Joiner.on(",").join(errors.get("errors").toArray()));
            setData(errors);
        }};
    }

    public static RestResult invalidParams(Multimap<String, String> errors) {
        return new RestResult() {{
            setCode(ResultCode.INVALID_PARAMS_ERROR.getCode());
            setMsg(Joiner.on(",").join(errors.get("errors").toArray()));
        }};
    }

    public static RestResult invalidParams() {
        return new RestResult(ResultCode.INVALID_PARAMS_ERROR);
    }

    public static RestResult invalidParams(String msg) {
        return new RestResult() {{
            setCode(ResultCode.INVALID_PARAMS_ERROR.getCode());
            setMsg(msg);
        }};
    }

}
