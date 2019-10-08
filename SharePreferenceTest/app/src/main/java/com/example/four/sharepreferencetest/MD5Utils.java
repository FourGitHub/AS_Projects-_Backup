package com.example.four.sharepreferencetest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2018/1/30 0030.
 * MD 5 加密
 */

public class MD5Utils {
    public static String digest(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 把一个byte数组转换成加密后的数组
            byte[] bytes = digest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                int c = b & 0xff; /******/
                String result = Integer.toHexString(c);// 取得十六进制字符串
                if (result.length() < 2) {
                    stringBuilder.append("0");// 让16进制数全都变成两位
                }
                stringBuilder.append(result);
            }
            return stringBuilder.toString();// 返回加密后的密文
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return  "";
        }

    }
}
