package com.apkfuns.androidgank.ui.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.utils.NetUtil;
import com.apkfuns.androidgank.utils.OkHttpClientManager;
import com.squareup.okhttp.Request;

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

    public void setTitle(String title) {
        getActivity().setTitle(title);
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
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                String errorMsg = getString(R.string.msg_network_unreachable);
                if (request != null && request.body() != null) {
                    errorMsg = request.body().toString();
                } else if (e != null) {
                    errorMsg = e.getMessage();
                }
                onRequestCallBack(requestCode, errorMsg, false);
            }

            @Override
            public void onResponse(String response) {
                onRequestCallBack(requestCode, response, true);
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
        return null;
    }

    @Override
    public void startActivity(Class cla) {
        startActivity(cla);
    }

    @Override
    public void startActivity(Class cla, Bundle bundle) {
        Intent it = new Intent(getActivity(), cla);
        it.putExtras(bundle);
        startActivity(it);
    }

    @Override
    public AlertDialog showMenu(String[] menus, AdapterView.OnItemClickListener listener) {
        Context context = getActivity();
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        ListView popMenu = new ListView(context);
        popMenu.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, menus));
        popMenu.setOnItemClickListener(listener);
        dialog.setView(popMenu);
        dialog.show();
        return dialog;
    }

}
