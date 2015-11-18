package com.apkfuns.androidgank.ui;

import com.apkfuns.androidgank.ui.base.BaseFragment;
import com.apkfuns.androidgank.ui.base.BaseTabActivity;
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
        addTitleAndFragment("技术博文", new BaseFragment());
        addTitleAndFragment("大牛博主", new BaseFragment());
        addTitleAndFragment("开源社区", new BaseFragment());
        tabShow();
    }
}
