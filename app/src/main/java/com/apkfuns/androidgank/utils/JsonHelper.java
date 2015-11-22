package com.apkfuns.androidgank.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

/**
 * Created by pengwei08 on 2015/7/31.
 * gson帮助类
 */
public class JsonHelper {
    private static Gson singleton;

    private JsonHelper() {

    }

    /**
     * 单例
     *
     * @return
     */
    public static Gson getInstance() {
        return singleton == null ? singleton = new Gson() : singleton;
    }

    /**
     * 解析json
     *
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return fromJsonWithException(json, classOfT);
        } catch (IllegalStateException | JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * 解析json并抛出异常
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     * @throws IllegalStateException
     * @throws JsonSyntaxException
     */
    public static <T> T fromJsonWithException(String json, Class<T> classOfT)
            throws IllegalStateException, JsonSyntaxException {
        return getInstance().fromJson(json, classOfT);
    }

    /**
     * 对象转json
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        return getInstance().toJson(src);
    }
}
