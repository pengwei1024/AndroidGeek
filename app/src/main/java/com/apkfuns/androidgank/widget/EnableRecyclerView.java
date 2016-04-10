package com.apkfuns.androidgank.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by pengwei on 15/11/28.
 */
public class EnableRecyclerView extends RecyclerView {
    public EnableRecyclerView(Context context) {
        super(context);
    }

    public EnableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EnableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    protected void onMeasure(int widthSpec, int heightSpec) {
//        int newHeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthSpec, newHeightSpec);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        return true;
//    }
}
