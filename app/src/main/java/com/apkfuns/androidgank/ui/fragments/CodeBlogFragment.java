package com.apkfuns.androidgank.ui.fragments;

import com.apkfuns.androidgank.ui.base.BaseTabFragment;

/**
 * Created by pengwei08 on 15/11/16.
 */
public class CodeBlogFragment extends BaseTabFragment {

    public static CodeBlogFragment getInstance() {
        return new CodeBlogFragment();
    }

    @Override
    protected void initTab() {
        setTitle("技术博客");
        addTitleAndFragment("技术博文", ArticleFragment.getInstance());
        addTitleAndFragment("大牛博主", CodeAuthorFragment.getInstance("1"));
        addTitleAndFragment("开源社区", CodeAuthorFragment.getInstance("2"));
        tabShow();
    }
}
