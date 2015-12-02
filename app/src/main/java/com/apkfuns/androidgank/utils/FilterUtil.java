/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.apkfuns.androidgank.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pengwei08 on 2015/8/10.
 * 过滤输入内容
 */
public class FilterUtil {

    /**
     * 名称只允许输入中文和英文
     *
     * @param name
     * @return
     */
    public static boolean nameFilter(String name) {
        return booleanFilter("^[A-Z|a-z|\\u4E00-\\u9FA5][A-Z|a-z|\\u4E00-\\u9FA5|\\s]+$", name);
    }

    /**
     * 匹配字符串
     *
     * @param pattern
     * @param match
     * @return
     */
    public static boolean booleanFilter(String pattern, String match) {
        Pattern p = Pattern.compile(pattern);
        return booleanFilter(p, match);
    }

    /**
     * 匹配字符串
     *
     * @param pattern
     * @param match
     * @return
     */
    public static boolean booleanFilter(Pattern pattern, String match) {
        Matcher matcher = pattern.matcher(match);
        return matcher.matches();
    }

    /**
     * 是否全部是数字
     *
     * @param str
     * @return
     */
    public static boolean isAllNumber(String str) {
        for (int i = 0; i < str.length(); ++i) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为图片路径
     *
     * @param imageUrl
     * @return
     */
    public static boolean isImageUrl(String imageUrl) {
        String patternString = "^(http|https)://.+\\.(gif|png|jpg|jpeg|bmp)$";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        return booleanFilter(pattern, imageUrl);
    }
}
