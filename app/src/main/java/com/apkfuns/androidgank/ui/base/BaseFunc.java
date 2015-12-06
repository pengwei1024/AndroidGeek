package com.apkfuns.androidgank.ui.base;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import com.squareup.okhttp.Callback;

/**
 * Created by pengwei on 15/11/14.
 */
interface BaseFunc {
    /**
     * 对象不为空
     *
     * @param object
     */
    boolean notNull(Object object);

    /**
     * 字符串不为空
     *
     * @param string
     */
    boolean notEmpty(String string);

    /**
     * toast提示
     *
     * @param msg
     */
    void toast(String msg, Object... args);

    /**
     * 导入布局
     *
     * @param layoutId
     * @return
     */
    <T extends View> T inflateView(int layoutId);


    /* --------网络请求------- */

    /**
     * 网络请求回调
     *
     * @param requestCode
     * @param result
     * @param success
     */
    void onRequestCallBack(int requestCode, String result, boolean success);

    /**
     * 异步请求
     *
     * @param url
     * @param requestCode
     * @param args
     */
    void asyncGet(String url, int requestCode, String... args);

    /**
     * 同步请求，需要自己回调onRequestCallBack()方法
     *
     * @param url
     * @param requestCode
     * @param args
     * @return
     */
    String syncGet(String url, int requestCode, String... args);

    /**
     * 启动activity
     *
     * @param cla
     */
    void startActivity(Class cla);

    void startActivity(Class cla, Bundle bundle);

    /**
     * 显示菜单
     *
     * @param menus
     * @param listener
     */
    AlertDialog showMenu(String[] menus, AdapterView.OnItemClickListener listener);

}
