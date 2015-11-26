package com.apkfuns.androidgank.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by pengwei on 15/11/26.
 */
public class ViewPagerExtend extends ViewPager {
    public ViewPagerExtend(Context context) {
        super(context);
    }

    public ViewPagerExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}

