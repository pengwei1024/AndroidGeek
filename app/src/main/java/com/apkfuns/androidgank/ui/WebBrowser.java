package com.apkfuns.androidgank.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.apkfuns.androidgank.app.App;
import com.apkfuns.androidgank.ui.base.BaseWebActivity;

/**
 * Created by pengwei08 on 2015/8/14.
 */
public class WebBrowser extends BaseWebActivity {
    protected String url;
    protected String content;
    private String tempUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        content = getIntent().getStringExtra("url");
        if (notNull(url)) {
            super.loadUrl(url);
        } else if (notNull(content)) {
            super.loadData(content);
        }
        addJavascriptInterface(new OnJavaScriptLocalObject(), "localObject");
        setTitle("正在加载...");
    }

    @Override
    public void onReceivedError(int errorCode, String description, String failingUrl) {
        tempUrl = failingUrl;
        loadUrlFromAsset("html/error.html");
    }

    /**
     * 本地javascript对象
     */
    class OnJavaScriptLocalObject {
        // 重新加载
        @JavascriptInterface
        public void reload() {
            loadUrl(tempUrl);
        }
    }

    /**
     * 加载页面
     *
     * @param url
     */
    public static void openUrl(String url) {
        Context context = App.get();
        Intent it = new Intent(context, WebBrowser.class);
        it.putExtra("url", url);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }

    /**
     * 从assets加载
     *
     * @param assetPath
     */
    public static void openFromAssets(String assetPath) {
        openUrl("file:///android_asset/" + assetPath);
    }

    /**
     * 加载文本
     *
     * @param data
     */
    public static void openData(String data) {
        Context context = App.get();
        Intent it = new Intent(context, WebBrowser.class);
        it.putExtra("content", data);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }
}
