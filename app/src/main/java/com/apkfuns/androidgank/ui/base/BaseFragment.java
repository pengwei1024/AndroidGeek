package com.apkfuns.androidgank.ui.base;


import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.utils.NetUtil;
import com.apkfuns.androidgank.utils.OkHttpUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class BaseFragment extends Fragment implements BaseFunc {

    @Override
    public boolean notNull(Object object) {
        return object != null;
    }

    @Override
    public boolean notEmpty(String string) {
        return !TextUtils.isEmpty(string);
    }

    @Override
    public void toast(String msg, Object... args) {
        Toast.makeText(getActivity(), String.format(msg, args), Toast.LENGTH_LONG).show();
    }

    @Override
    public View inflateView(int layoutId) {
        return LayoutInflater.from(getActivity()).inflate(layoutId, null);
    }

    /*  -------------网络请求------------ */
    @Override
    public void onRequestCallBack(int requestCode, String result, boolean success) {
        if (!NetUtil.isConnect(getActivity())) {
            toast("请保持网络连接");
        } else if (!success) {
            toast("请求出现异常");
        }
    }

    @Override
    public void asyncGet(String url, final int requestCode, String... args) {
        if (notNull(args) && args.length > 0 && args.length % 2 == 0) {
            url += (url.contains("?") ? "" : "?");
            for (int i = 0; i < args.length; i = i + 2) {
                url += String.format("&%s=%s", args[i], args[i + 1]);
            }
        }
        OkHttpUtils.asyncGet(url, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String errorMsg = getString(R.string.msg_network_unreachable);
                if (request != null && request.body() != null) {
                    errorMsg = request.body().toString();
                } else if (e != null) {
                    errorMsg = e.getMessage();
                }
                onRequestCallBack(requestCode, errorMsg, false);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    onRequestCallBack(requestCode, response.body().toString(), true);
                } else {
                    String msg = notNull(response) ? response.body().toString() : "请求异常";
                    onRequestCallBack(requestCode, msg, false);
                }
            }
        });
    }

    @Override
    public String syncGet(String url, int requestCode, String... args) {
        if (notNull(args) && args.length > 0 && args.length % 2 == 0) {
            url += (url.contains("?") ? "" : "?");
            for (int i = 0; i < args.length; i = i + 2) {
                url += String.format("&%s=%s", args[i], args[i + 1]);
            }
        }
        try {
            return OkHttpUtils.syncGet(url).body().toString();
        } catch (IOException e) {
            return null;
        }
    }

}
