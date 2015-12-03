package com.apkfuns.androidgank.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.base.BaseActivity;

/**
 * Created by pengwei on 15/12/2.
 */
public class SplashActivity extends BaseActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findView(R.id.imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
                finish();
            }
        }, 2500);
    }

    @Override
    protected boolean showBackIcon() {
        return false;
    }

    @Override
    protected boolean showToolBar() {
        return false;
    }
}
