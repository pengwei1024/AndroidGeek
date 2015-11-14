package com.apkfuns.androidgank.ui.base;

import android.view.View;

/**
 * Created by pengwei on 15/11/14.
 */
public interface BaseFunc {
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
    void toast(String msg, Object...args);

    /**
     * 导入布局
     * @param layoutId
     * @param <T>
     * @return
     */
    <T extends View> T inflateView(int layoutId);

}
