package com.apkfuns.androidgank.utils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class OkHttpUtils {
    private static OkHttpClient httpClient = new OkHttpClient();

    /**
     * 同步GET请求
     *
     * @param url
     * @throws IOException
     */
    public static Response syncGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response;
    }

    /**
     * 异步GET请求
     *
     * @param url
     * @param callback
     */
    public static void asyncGet(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        httpClient.newCall(request).enqueue(callback);
    }
}
