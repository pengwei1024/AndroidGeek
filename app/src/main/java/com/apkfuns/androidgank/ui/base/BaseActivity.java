package com.apkfuns.androidgank.ui.base;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.helper.LayoutParamsHelper;

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
    public <T extends View> T inflateView(int layoutId) {
        return (T) LayoutInflater.from(this).inflate(layoutId, null);
    }

    public <T extends View> T findView(int viewId) {
        return (T) findViewById(viewId);
    }


}
