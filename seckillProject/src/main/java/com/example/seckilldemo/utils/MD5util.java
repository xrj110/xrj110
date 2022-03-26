package com.example.seckilldemo.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    private final static  String salt="hf12skd2";
    public static String inputPassToFromPass(String inputPass){
        String str=""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }
    public static String fromPassToDBPass(String form,String salt){
        String str=salt.charAt(0)+salt.charAt(2)+form;
        return md5(str);
    }
    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass=inputPassToFromPass(inputPass);
        String dbPass=fromPassToDBPass(fromPass,salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(MD5util.inputPassToDBPass("123456","j9d191281"));
//        System.out.println(fromPassToDBPass("884d593956938249502aa6c7049edabb","j9d191281"));
    }
}
