package com.crazychat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * 
 * @author RuanYaofeng
 * @date 2018年4月15日 下午3:10:29
 */
public class EncryptionUtils {

    /**
     * 获取MD5加密字符串
     * 
     * @param str 待加密字符串
     * @return 返回32位MD5字符串，若出现异常则返回null
     */
    public static String getMD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // 理论上不会出现此异常
            e.printStackTrace();
            return null;
        }
        byte[] byteArray;
        try {
            byteArray = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // 理论上不会出现此异常
            e.printStackTrace();
            return null;
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * SHA-1加密
     * 
     * @param str 待加密字符串
     * @return 返回40位SHA字符串，若出现异常则返回null
     */
    public static String getSHA1(String str) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            // 理论上不会出现此异常
            e.printStackTrace();
            return null;
        }
        byte[] byteArray;
        try {
            byteArray = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // 理论上不会出现此异常
            e.printStackTrace();
            return null;
        }
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
