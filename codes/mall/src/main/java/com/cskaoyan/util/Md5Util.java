package com.cskaoyan.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    public static String getMd5(String content) throws Exception {
        //获得消息摘要算法
        MessageDigest md5 = MessageDigest.getInstance("md5");
        //用户传来的密码
        byte[] contentBytes = content.getBytes();
        //生成散列
        byte[] resultBytes = md5.digest(contentBytes);
        int length = resultBytes.length;
        System.out.println("length :" + length);
        StringBuffer stringBuffer = new StringBuffer();
        for (byte resultByte : resultBytes) {
            int temp = resultByte&0xFF;
            String s = Integer.toHexString(temp);
            if (s.length() == 1) {
                stringBuffer.append(0);
            }
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }

    public static String getMd5(String content,String salt) throws Exception {
        content = content + salt + "_1452";
        return getMd5(content);
    }

    public static String getFileMd5(File file) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int length = 0;
        while ((length = fileInputStream.read(bytes,0,1024)) != -1) {
            md5.update(bytes,0,bytes.length);
        }
        byte[] resultBytes = md5.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte resultByte : resultBytes) {
            int temp = resultByte&0xFF;
            String s = Integer.toHexString(temp);
            if (s.length() == 1) {
                stringBuffer.append(0);
            }
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }
}
