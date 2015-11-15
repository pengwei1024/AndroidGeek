package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.models.GankWelfareItem;
import com.apkfuns.androidgank.ui.WebBrowser;
import com.apkfuns.androidgank.ui.base.BaseListFragment;
import com.apkfuns.simplerecycleradapter.RVHolder;
import com.apkfuns.simplerecycleradapter.SimpleRecyclerAdapter;
import com.umeng.comm.ui.activities.BrowserActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengwei08 on 15/11/15.
 */
public class GankContentItemFragment extends BaseListFragment {

    private static final String DATA_LIST = "data_list";

    public static GankContentItemFragment getInstance(ArrayList<GankWelfareItem.ResultsEntity> list) {
        GankContentItemFragment fragment = new GankContentItemFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final List<GankWelfareItem.ResultsEntity> dataList = (List<GankWelfareItem.ResultsEntity>) getArguments().getSerializable(DATA_LIST);
        setAdapter(new SimpleRecyclerAdapter<GankWelfareItem.ResultsEntity>(
                R.layout.adapter_gank_content_item, dataList) {
            @Override
            public void onBindView(RVHolder holder, int position, int itemViewType, GankWelfareItem.ResultsEntity item) {
                holder.setTextView(R.id.title, item.getDesc()).setTextView(R.id.date, item.getPublishedAt())
                        .setTextView(R.id.author, item.getWho());
            }

            @Override
            public void onItemClick(RVHolder holder, View view, int position, GankWelfareItem.ResultsEntity item) {
                WebBrowser.openUrl(item.getUrl());
            }
        });
    }

    @Override
    protected boolean onLoadMoreEnable() {
        return false;
    }

    @Override
    protected boolean onRefreshEnable() {
        return false;
    }
}
