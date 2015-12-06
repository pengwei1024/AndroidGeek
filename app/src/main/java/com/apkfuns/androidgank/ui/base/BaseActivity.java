package com.apkfuns.androidgank.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.helper.LayoutParamsHelper;
import com.apkfuns.androidgank.utils.NetUtil;
import com.apkfuns.androidgank.utils.OkHttpClientManager;
import com.squareup.okhttp.Request;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class BaseActivity extends AppCompatActivity implements BaseFunc {

    /**
     * 设置布局文件
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        setContentView(inflateView(layoutResID));
    }

    public void setContentView(View view) {
        if (showToolBar()) {
            if (notNull(view.findViewById(R.id.toolbar))) {
                super.setContentView(view);
            } else {
                LinearLayout parent = new LinearLayout(this);
                parent.setOrientation(LinearLayout.VERTICAL);
                View toolBarView = inflateView(R.layout.view_toolbar);
                parent.addView(toolBarView, LayoutParamsHelper.getLinearLayoutParamWrap());
                parent.addView(view, LayoutParamsHelper.getLinearLayoutParamMatch());
                super.setContentView(parent);
            }
            setToolBar();
        } else {
            super.setContentView(view);
        }
    }

    private Toolbar toolbar;

    /**
     * 是否显示toolBar
     *
     * @return
     */
    protected boolean showToolBar() {
        return true;
    }

    /**
     * 是否显示返回键
     *
     * @return
     */
    protected boolean showBackIcon() {
        return true;
    }

    /**
     * 设置toolBar
     */
    protected void setToolBar() {
        setSupportActionBar(getToolBar());
        ActionBar actionBar = getSupportActionBar();
        if (notNull(actionBar) && showBackIcon()) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 获取toolBar对象
     *
     * @return
     */
    protected Toolbar getToolBar() {
        if (!showToolBar()) {
            throw new IllegalArgumentException(getString(R.string.no_find_toolbar));
        }
        if (toolbar != null) {
            return toolbar;
        }
        return toolbar = findView(R.id.toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /* --------------常用方法-------------- */

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
        Toast.makeText(this, String.format(msg, args), Toast.LENGTH_LONG).show();
    }

    @Override
    public View inflateView(int layoutId) {
        return LayoutInflater.from(this).inflate(layoutId, null, false);
    }

    public <T extends View> T findView(int viewId) {
        return (T) findViewById(viewId);
    }

    /*  -------------网络请求------------ */
    @Override
    public void onRequestCallBack(int requestCode, String result, boolean success) {
        if (!NetUtil.isConnect(this)) {
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
        return null;
    }

    @Override
    public void startActivity(Class cla) {
        startActivity(cla, null);
    }

    @Override
    public void startActivity(Class cla, Bundle bundle) {
        Intent it = new Intent(this, cla);
        if (notNull(bundle)) {
            it.putExtras(bundle);
        }
        startActivity(it);
    }

    @Override
    public AlertDialog showMenu(String[] menus, AdapterView.OnItemClickListener listener) {
        Context context = this;
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        ListView popMenu = new ListView(context);
        popMenu.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, menus));
        popMenu.setOnItemClickListener(listener);
        dialog.setView(popMenu);
        dialog.show();
        return dialog;
    }
}
