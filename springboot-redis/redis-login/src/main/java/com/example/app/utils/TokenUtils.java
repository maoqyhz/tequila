package com.example.app.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * @author Fururur
 * @create 2019-12-16-10:54
 */
public class TokenUtils {

    public static String generateToken(String salt) {
        return DigestUtils.sha1Hex((salt + System.currentTimeMillis()).getBytes());
    }

    public static String getRandomStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * md5 encrypt
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str.getBytes());
    }

    /**
     * encrypt
     *
     * @param str
     * @param salt
     * @return
     */
    public static String md5(String str, String salt) {
        return DigestUtils.md5Hex((str + salt).getBytes());
    }

    public static void main(String[] args) {

    }
}
