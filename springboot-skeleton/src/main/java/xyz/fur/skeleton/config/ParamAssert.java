package xyz.fur.skeleton.config;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Data;
import org.springframework.validation.BindingResult;
import xyz.fur.skeleton.entity.base.ResultCode;

import javax.annotation.Nullable;

/**
 * @author Fururur
 * @date 2020/5/26-19:22
 */
@Data
public class ParamAssert {

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new ServiceException(ResultCode.INVALID_PARAMS_ERROR,
                    Strings.isNullOrEmpty(message) ? ResultCode.INVALID_PARAMS_ERROR.getMsg() : message);
        }
    }

    /**
     * 所有参数均为null时，抛出异常
     *
     * @param objects
     */
    public static void notNullAll(String message, @Nullable Object... objects) {
        assert objects != null;
        int len = objects.length;
        int i = 0;
        for (Object o : objects) {
            if (o instanceof String) {
                if (Strings.isNullOrEmpty(String.valueOf(o))) {
                    i++;
                }
            } else {
                if (o == null) {
                    i++;
                }
            }
        }
        if (i == len) {
            throw new ServiceException(ResultCode.INVALID_PARAMS_ERROR, message);
        }
    }

    /**
     * 有null的参数时就抛出异常
     *
     * @param objects
     */
    public static void notNullAny(String message, @Nullable Object... objects) {
        assert objects != null;
        for (Object o : objects) {
            if (o instanceof String) {
                notBlank(String.valueOf(o), Strings.isNullOrEmpty(message) ? "所有参数均不能为空或null" : message);
            } else {
                notNull(o, Strings.isNullOrEmpty(message) ? "所有参数均不能为空或null" : message);
            }
        }
    }

    public static void notBlank(@Nullable String str, String message) {
        if (Strings.isNullOrEmpty(str)) {
            throw new ServiceException(ResultCode.INVALID_PARAMS_ERROR,
                    Strings.isNullOrEmpty(message) ? ResultCode.INVALID_PARAMS_ERROR.getMsg() : message);
        }
    }

    public static void checkBindingResult(BindingResult object) {
        if (object.hasErrors()) {
            Multimap<String, String> errors = ArrayListMultimap.create();
            object.getFieldErrors().forEach(e -> errors.put("errors", e.getDefaultMessage()));
            throw new ServiceException(ResultCode.INVALID_PARAMS_ERROR,
                    Joiner.on(",").join(errors.get("errors").toArray()));
        }
    }
}
