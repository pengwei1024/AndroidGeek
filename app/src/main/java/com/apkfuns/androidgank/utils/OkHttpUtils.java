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
    private OkHttpClient httpClient = new OkHttpClient();

    public void syncGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {

        }
    }

    public void asyncGet(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        httpClient.newCall(request).enqueue(callback);
    }
}
