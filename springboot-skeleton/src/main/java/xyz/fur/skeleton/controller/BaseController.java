package xyz.fur.skeleton.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.fur.skeleton.config.CustomProperties;
import xyz.fur.skeleton.entity.base.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fururur
 * @create 2019-07-23-11:03
 */
public class BaseController {

    @Autowired
    protected CustomProperties prop;

    /**
     * parse前端json字符串
     * @param request
     * @return
     */
    protected JSONObject parserJson(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return JSONObject.parseObject(buffer.toString());
    }

    /**
     * 简单map返回
     * @param code
     * @param o
     * @return
     */
    protected Map<String, Object> getResult(int code, Object o) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("data", o);
        result.put("msg", code == 0 ? "" : ResultCode.toMsg(code));
        return result;
    }
}
