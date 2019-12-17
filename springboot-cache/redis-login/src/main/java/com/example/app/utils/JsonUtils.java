package com.example.app.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * @author Fururur
 * @create 2019-12-16-10:21
 */
public class JsonUtils {
    /**
     * parse前端json字符串
     *
     * @param request
     * @return
     */
    public static JSONObject parserJson(HttpServletRequest request) {
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
}
