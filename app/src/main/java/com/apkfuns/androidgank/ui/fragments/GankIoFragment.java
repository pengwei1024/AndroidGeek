package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.apkfuns.androidgank.app.Global;
import com.apkfuns.androidgank.models.GankWelfareItem;
import com.apkfuns.androidgank.ui.base.BaseListFragment;
import com.apkfuns.androidgank.utils.JsonHelper;
import com.apkfuns.simplerecycleradapter.SimpleRecyclerAdapter;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class GankIoFragment extends BaseListFragment {

    private int page;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRecyclerView().setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL));
    }

    private void getDataByPage(int page) {
        asyncGet(String.format(Global.GET_GANK_WELFARE, page), 0);
    }

    @Override
    public void onRequestCallBack(int requestCode, String result, boolean success) {
        super.onRequestCallBack(requestCode, result, success);
        if (success) {
            GankWelfareItem item = JsonHelper.fromJson(result, GankWelfareItem.class);
            if (notNull(item)) {

            }
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
