package com.apkfuns.androidgank.network;

import android.text.TextUtils;

import com.apkfuns.androidgank.app.Global;
import com.apkfuns.androidgank.utils.EncodeUtil;
import com.apkfuns.androidgank.utils.JsonHelper;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengwei on 15/11/21.
 */
public class AVQuery {

    public static final String BASE_URL = "https://leancloud.cn:443/1.1/classes/";

    /**
     * AV get请求
     *
     * @param clazz
     * @param lastTime
     * @param callback
     */
    public static void AVGet(String clazz, String lastTime, ResultCallback callback) {
        String url = BASE_URL + clazz + "?limit=10&&order=-createAt";
        if (!TextUtils.isEmpty(lastTime)) {
            url += "&&where={\"createdAt\":{\"$lt\":{\"__type\":\"Date\",\"iso\":\"" + lastTime + "\"}}}";
        }
        String time = String.valueOf(System.currentTimeMillis());
        String sign = EncodeUtil.md5(time + Global.AV_APP_KEY) + "," + time;
        new OkHttpRequest.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("X-LC-Sign", sign)
                .addHeader("X-LC-Id", Global.AV_APP_ID)
                .url(url)
                .get(callback);
    }

    public static <T extends AVObject> void AVGet(String className, String params, final Class<T> cla,
                                                  final AvCallBack<T> callBack) {
        String url = BASE_URL + className;
        if (!TextUtils.isEmpty(params)) {
            url += params.contains("?") ? params : "?" + params;
        }
        String time = String.valueOf(System.currentTimeMillis());
        String sign = EncodeUtil.md5(time + Global.AV_APP_KEY) + "," + time;
        ResultCallback<String> callback = new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                callBack.onResult(null, e);
            }

            @Override
            public void onResponse(String string) {
                List<T> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONObject(string).getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(JsonHelper.fromJson(jsonArray.getString(i), cla));
                    }
                    callBack.onResult(list, null);
                } catch (JSONException e) {
                    callBack.onResult(null, e);
                }
            }
        };
        new OkHttpRequest.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("X-LC-Sign", sign)
                .addHeader("X-LC-Id", Global.AV_APP_ID)
                .url(url)
                .get(callback);
    }

    /**
     * 获取文章列表
     *
     * @param callBack
     */
    public static void GetArticle(AvCallBack callBack, String lastTime) {
        String param = "?limit=10&&order=-createAt";
        if (!TextUtils.isEmpty(lastTime)) {
            param += "&&where={\"createdAt\":{\"$lt\":{\"__type\":\"Date\",\"iso\":\"" + lastTime + "\"}}}";
        }
        AVGet("Articles", param, AVObject.class, callBack);
    }

    /**
     * AV请求回调方法
     * @param <T>
     */
    public interface AvCallBack<T> {
        /**
         * 请求成功返回List，失败返回Exception
         *
         * @param list
         * @param e
         */
        void onResult(List<T> list, Exception e);
    }
}
