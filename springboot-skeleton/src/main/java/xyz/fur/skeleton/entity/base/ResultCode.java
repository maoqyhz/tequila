package xyz.fur.skeleton.entity.base;

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
    SUCCESS(0,"Success"),
    INVALID_PARAMS_ERROR(-5111, "传输参数不正确"),
    IMAGE_SIZE_EXCEED_ERROR(-5112, "图片大小超过5M限制"),
    INVALID_IMAGE_FORMAT_ERROR(-5113, "图片格式不正确，需上传JPG格式"),
    REQUEST_FAILED_ERROR(-5114, "后台请求返回失败");

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
