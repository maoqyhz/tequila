package com.example.api.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Fururur
 * @create 2019-12-23-9:28
 */
@Slf4j
public class SignUtil {

    /**
     * 计算签名
     *
     * @param uri    ip:port后的请求路径
     * @param params url后的参数
     * @param body   form-data键值对
     * @return
     */
    public static String sign(String ak, String sk,
                              String uri, Map<String, String> params,
                              Object body) {
        return signature(sk, buildStringToSign(ak, uri, params, body));
    }

    private static String signature(String sk, String content) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = sk.getBytes(StandardCharsets.UTF_8);
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
            return Base64.getEncoder().encodeToString(
                    hmacSha256.doFinal(content.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成 strToSign
     * 1. 约定请求时会携带 ak 作为参数并放入 HTTP Header。
     * 2. 请求参数处理：
     *    - GET：取出所有的参数，并根据 key 进行字典排序，拼装成如下格式。
     *    - POST：如果是 `application/x-www-form-urlencoded`，直接取出和 GET 参数进行排序拼接，
     *      如果是 `application/json`，则直接将整个 json 串 md5 加密后再 base64。
     *
     * GET:
     * strToSign = uri + "\n" + ak=ak&k1=v1&k2=v2&k3=v3
     * POST:
     * strToSign = uri + "\n" + ak=ak&k1=v1Z&k2=v2&k3=v3 + "\n" + base64(md5(json_body))
     *
     * @param ak
     * @param uri
     * @param params
     * @param body
     * @return
     */
    private static String buildStringToSign(String ak, String uri, Map<String, String> params,
                                            Object body) {
        StringBuilder sb = new StringBuilder();
        sb.append(uri).append("\n").append("ak").append("=").append(ak).append("&");
        if (body instanceof String) {
            StringBuilder sbParams = buildResource(params, null);
            String encryptedBody = MessageDigestUtil.base64AndMD5((String) body);
            if (sbParams != null)
                sb.append(sbParams);
            sb.append("\n").append(encryptedBody);
            log.debug((String) body);
            log.debug(encryptedBody);
        } else {
            StringBuilder sbParams = buildResource(params, (Map<String, String>) body);
            if (sbParams != null)
                sb.append(sbParams);
        }
        log.debug(sb.toString());
        return sb.toString();
    }

    /**
     * kv型参数拼接
     *
     * @param params
     * @param body
     * @return
     */
    private static StringBuilder buildResource(Map<String, String> params, Map<String, String> body) {
        // 参数字典排序
        // url params 和 form-data 均为键值对，一起组装
        Map<String, String> sortedMap = new TreeMap<>();
        if (null != params) {
            params.forEach((k, v) -> {
                if (!StringUtils.isBlank(k))
                    sortedMap.put(k, v);
            });
        }
        if (null != body) {
            body.forEach((k, v) -> {
                if (!StringUtils.isBlank(k))
                    sortedMap.put(k, v);
            });
        }
        // 参数拼接
        // 形式为 k=v&k=v&k=v...
        StringBuilder sbParams = new StringBuilder();
        sortedMap.forEach((k, v) -> {
            if (0 < sbParams.length()) {
                sbParams.append("&");
            }
            sbParams.append(k);
            if (!StringUtils.isBlank(v))
                sbParams.append("=").append(v);
        });
        return sbParams;
    }

    public static void main(String[] args) {
        String ak = "1q2w3e4r";
        String sk = "5tg6yh7uj";
        String uri = "/api/query";
        // Map<String, String> params = new HashMap<>();
        // params.put("date", "20191224");
        // params.put("city", "hangzhou");
        Map<String, String> body = new HashMap<>();

        body.put("date", "20191224");
        body.put("city", "hangzhou");
        System.out.println(sign(ak, sk, uri, null, JSONObject.toJSONString(body)));
    }
}
