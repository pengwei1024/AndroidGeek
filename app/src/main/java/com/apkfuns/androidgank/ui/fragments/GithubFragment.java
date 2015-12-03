package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.base.BaseFragment;
import com.apkfuns.androidgank.ui.base.BaseTabFragment;
import com.apkfuns.logutils.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by pengwei on 15/12/3.
 */
public class GitHubFragment extends BaseTabFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initTab() {
        addTitleAndFragment("Repositories", GitHubListFragment.getInstance("https://github.com/trending?l=java"));
        addTitleAndFragment("Developers", GitHubListFragment.getInstance("https://github.com/trending/developers?l=java"));
        tabShow();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_github_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
