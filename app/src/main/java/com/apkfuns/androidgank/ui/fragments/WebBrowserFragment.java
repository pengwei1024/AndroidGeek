package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.base.BaseFragment;

/**
 * Created by pengwei08 on 15/11/15.
 */
public class WebBrowserFragment extends BaseFragment {

    public static WebBrowserFragment getInstance(String url) {
        WebBrowserFragment fragment = new WebBrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_base_browser, null, false);
        WebView webView = (WebView) rootView.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getArguments().getString("url"));
        return rootView;
    }
}
