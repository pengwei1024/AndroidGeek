package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.base.BaseTabFragment;



/**
 * Created by pengwei on 15/12/3.
 */
public class GitHubFragment extends BaseTabFragment {

    private static GitHubFragment singleton;

    public static GitHubFragment getInstance() {
        return singleton == null ? singleton = new GitHubFragment() : singleton;
    }

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
