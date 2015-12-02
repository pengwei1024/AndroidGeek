package com.apkfuns.androidgank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.adapter.BaseTabAdapter;
import com.apkfuns.androidgank.ui.fragments.TestFragment;

/**
 * Created by pengwei on 15/11/28.
 */
public class GankContent2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("123456");

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        scrollView.setFillViewport(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new BaseTabAdapter(getSupportFragmentManager(),
                new String[]{"A", "B", "C"}, new Fragment[]{
                new TestFragment(), new TestFragment(), new TestFragment()
        }));
        tabLayout.setupWithViewPager(viewPager);
    }
}
