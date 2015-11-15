package com.apkfuns.androidgank.ui.base;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.apkfuns.androidgank.R;

/**
 * Created by pengwei08 on 2015/5/21.
 */
public class BaseWebActivity extends BaseActivity {
    private WebView webView;
    private ProgressBar pb;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        webView = findView(R.id.webView);
        pb = findView(R.id.pb);
        if (!notNull(webView)) {
            throw new RuntimeException("Your content must have a WebView whose id a attribute is 'webView'");
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(chromeClient);
        webView.setWebViewClient(webViewClient);
    }

    /**
     * android和H5交互接口
     *
     * @param obj
     * @param interfaceName
     */
    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object obj, String interfaceName) {
        ensureWebViewExist();
        webView.addJavascriptInterface(obj, interfaceName);
    }

    /**
     * 获得webView对象
     *
     * @return
     */
    public WebView getWebView() {
        ensureWebViewExist();
        return webView;
    }

    /**
     * 未设定布局时加载默认webView
     */
    private void ensureWebViewExist() {
        if (!notNull(webView)) {
            setContentView(R.layout.activity_base_browser);
        }
    }

    /**
     * 返回键事件处理
     */
    public void onBackKeyPressed() {
        goBack();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackKeyPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取WebSettings对象
     *
     * @return
     */
    protected WebSettings getSettings() {
        ensureWebViewExist();
        return webView.getSettings();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ensureWebViewExist();
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 是否显示进度条
     *
     * @param flag
     */
    protected void showProgress(boolean flag) {
        pb.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    // WebChromeClient接口
    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            BaseWebActivity.this.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (notNull(pb)) {
                if (newProgress < 100) {
                    pb.setProgress(newProgress);
                } else {
                    showProgress(false);
                }
            }
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (notNull(consoleMessage)) {
                String message = String.format("[%s:%s] %s", consoleMessage.sourceId(), consoleMessage.lineNumber(),
                        consoleMessage.message());
                switch (consoleMessage.messageLevel()) {
                    case TIP:
//                        LogUtils.v(message);
                        break;
                    case LOG:
//                        LogUtils.i(message);
                        break;
                    case WARNING:
//                        LogUtils.w(message);
                        break;
                    case ERROR:
//                        LogUtils.e(message);
                        break;
                    case DEBUG:
//                        LogUtils.d(message);
                        break;
                    default:
                        break;
                }
                return true;
            }
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            toast(message);
            return true;
        }
    };

    // WebViewClient接口
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            BaseWebActivity.this.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (notNull(pb)) {
                pb.setVisibility(View.VISIBLE);
            }
            BaseWebActivity.this.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            showProgress(false);
            BaseWebActivity.this.onReceivedError(errorCode, description, failingUrl);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (notNull(pb)) {
                showProgress(true);
            }
            return BaseWebActivity.this.shouldOverrideUrlLoading(view, url);
        }
    };

    /**
     * 是否允许当前webView中打开网页
     *
     * @param view
     * @param url
     * @return
     */
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        webView.loadUrl(url);
//        LogUtils.e("url:" + url);
        return false;
    }

    /**
     * 网页加载完成
     *
     * @param view
     * @param url
     */
    public void onPageFinished(WebView view, String url) {

    }

    /**
     * 获得title
     *
     * @param view
     * @param title
     */
    public void onReceivedTitle(WebView view, String title) {
        setTitle(title);
    }

    /**
     * 网页开始加载
     *
     * @param view
     * @param url
     * @param favicon
     */
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    }

    /**
     * 网页加载错误
     *
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    public void onReceivedError(int errorCode, String description, String failingUrl) {

    }

    /**
     * 执行js
     *
     * @param javascript
     */
    protected void queryJS(String javascript) {
        ensureWebViewExist();
        webView.loadUrl("javascript:(function(){" + javascript + "})()");
    }

    /**
     * 通过id来隐藏控件
     *
     * @param ids
     */
    protected void removeTagById(String... ids) {
        String func = "";
        for (String id : ids) {
            func += "document.getElementById('" + id + "').style.display='none';";
        }
        queryJS(func);
    }

    /**
     * 返回到上一个页面
     */
    protected void goBack() {
        ensureWebViewExist();
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    /**
     * 加载网页
     *
     * @param url
     */
    protected void loadUrl(String url) {
        ensureWebViewExist();
        webView.loadUrl(url);
    }

    /**
     * 加载文本
     *
     * @param data
     */
    protected void loadData(String data) {
        ensureWebViewExist();
        webView.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
    }

    /**
     * 加载asset下网页
     *
     * @param path
     */
    protected void loadUrlFromAsset(String path) {
        loadUrl("file:///android_asset/" + path);
    }
}
