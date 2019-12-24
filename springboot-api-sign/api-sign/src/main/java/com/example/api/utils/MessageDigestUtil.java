package com.example.api.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Fururur
 * @create 2019-12-24-13:04
 */
@Slf4j
public class MessageDigestUtil {
    /**
     * 先进行MD5摘要再进行Base64编码获取摘要字符串
     *
     * @param str
     * @return
     */
    public static String base64AndMD5(String str) {
        if (str == null) {
            throw new IllegalArgumentException("inStr can not be null");
        }
        return base64AndMD5(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 先进行MD5摘要再进行Base64编码获取摘要字符串
     *
     * @return
     */
    public static String base64AndMD5(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes can not be null");
        }
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(bytes);
            final Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(md.digest());
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("unknown algorithm MD5");
        }
    }
}
