package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.base.BaseFragment;

/**
 * Created by pengwei08 on 15/11/15.
 */
public class WebBrowserFragment extends BaseFragment {

    public static WebBrowserFragment getInstance(String url) {
        return getInstance(url, null);
    }

    public static WebBrowserFragment getInstance(String url, String title) {
        WebBrowserFragment fragment = new WebBrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        if (!TextUtils.isEmpty(title)) {
            bundle.putString("title", title);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    private ProgressBar pb;
    private WebView webView;

    /**
     * 执行js
     *
     * @param javascript
     */
    public void queryJS(String javascript) {
        webView.loadUrl("javascript:" + javascript);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (notEmpty(getArguments().getString("title"))) {
            setTitle(getArguments().getString("title"));
        } else {
            setTitle("正在加载中...");
        }
        View rootView = inflater.inflate(R.layout.activity_base_browser, null, false);
        webView = (WebView) rootView.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!notEmpty(getArguments().getString("title"))) {
                    setTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if (newProgress >= 100) {
                    pb.setVisibility(View.GONE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                pb.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                if (url.startsWith("https://github.com")) {
//                    queryJS("document.getElementsByTagName('header')[0].style.display='none';");
//                }
            }
        });
        pb = (ProgressBar) rootView.findViewById(R.id.pb);
        webView.loadUrl(getArguments().getString("url"));
        return rootView;
    }
}
