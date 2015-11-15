package com.apkfuns.androidgank.ui.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.adapter.BaseTabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengwei08 on 15/11/15.
 */
public abstract class BaseTabActivity extends BaseActivity {
    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    protected BaseTabAdapter adapter;
    private String[] titles;
    private BaseFragment[] fragments;
    private List<String> titleList;
    private List<BaseFragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        tabLayout = findView(R.id.sliding_tabs);
        viewPager = findView(R.id.viewpager);
        initTab();
    }

    /**
     * 设置文件布局
     *
     * @return
     */
    protected int getContentView() {
        return R.layout.activity_base_tab;
    }

    /**
     * 初始化tab
     */
    protected abstract void initTab();

    /**
     * 设置tab数据
     *
     * @param titles
     * @param fragments
     */
    protected void setTabData(String[] titles, BaseFragment[] fragments) {
        adapter = new BaseTabAdapter(getSupportFragmentManager(), titles, fragments);
        setAdapter(adapter);
    }

    public void setTitle(String... titles) {
        setTitles(titles);
    }

    public void setTitles(String[] titles) {
        if (notNull(titles)) {
            this.titles = titles;
            if (notNull(fragments)) {
                setTabData(titles, fragments);
            }
        }
    }

    public void setFragment(BaseFragment... fragment) {
        setFragments(fragment);
    }

    public void setFragments(BaseFragment[] fragments) {
        if (notNull(fragments)) {
            this.fragments = fragments;
            if (notNull(titles)) {
                setTabData(titles, fragments);
            }
        }
    }

    /**
     * 加入title和fragment
     * 添加完成必须执行show()方法
     *
     * @param title
     * @param fragment
     */
    public void addTitleAndFragment(String title, BaseFragment fragment) {
        if (!notNull(titleList)) {
            titleList = new ArrayList<>();
        }
        if (!notNull(fragmentList)) {
            fragmentList = new ArrayList<>();
        }
        titleList.add(title);
        fragmentList.add(fragment);
    }

    /**
     * 必须在addTitleAndFragment()后执行
     */
    public void tabShow() {
        if (notNull(titleList) && notNull(fragmentList)) {
            setTabData(titleList.toArray(new String[0]), fragmentList.toArray(new BaseFragment[0]));
        }
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    protected void setAdapter(BaseTabAdapter adapter) {
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        if (adapter != null && adapter.getCount() > 3) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
    }
}
