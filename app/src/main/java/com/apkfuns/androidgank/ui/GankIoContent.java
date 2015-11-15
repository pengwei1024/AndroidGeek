package com.apkfuns.androidgank.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.base.BaseActivity;
import com.apkfuns.androidgank.ui.base.BaseFragment;
import com.apkfuns.androidgank.ui.base.BaseTabActivity;
import com.apkfuns.androidgank.ui.fragments.GankIoFragment;
import com.bumptech.glide.Glide;

/**
 * Created by pengwei08 on 15/11/15.
 */
public class GankIoContent extends BaseTabActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_gank_content;
    }

    @Override
    protected void initTab() {
        ImageView imageView = findView(R.id.backdrop);
        Glide.with(this).load("http://ww3.sinaimg.cn/large/7a8aed7bgw1exz7lm0ow0j20qo0hrjud.jpg")
                .into(imageView);
        CollapsingToolbarLayout collapsingToolbar = findView(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getTitle());
        setTitle("a", "b", "c");
        setFragment(new BaseFragment(), new BaseFragment(), new BaseFragment());
    }
}
