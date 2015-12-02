package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.models.GankWelfareItem;
import com.apkfuns.androidgank.ui.PhotoPreview;
import com.apkfuns.androidgank.ui.WebBrowser;
import com.apkfuns.androidgank.ui.base.BaseFragment;
import com.apkfuns.androidgank.utils.FilterUtil;
import com.apkfuns.androidgank.widget.EnableRecyclerView;
import com.apkfuns.simplerecycleradapter.RVHolder;
import com.apkfuns.simplerecycleradapter.SimpleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengwei on 15/11/28.
 */
public class GankContentArticleFragment extends BaseFragment {

    RecyclerView recyclerView;

    private static final String DATA_LIST = "data_list";

    public static GankContentArticleFragment getInstance(ArrayList<GankWelfareItem.ResultsEntity> list) {
        GankContentArticleFragment fragment = new GankContentArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new EnableRecyclerView(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final List<GankWelfareItem.ResultsEntity> dataList = (List<GankWelfareItem.ResultsEntity>) getArguments().getSerializable(DATA_LIST);
        recyclerView.setAdapter(new SimpleRecyclerAdapter<GankWelfareItem.ResultsEntity>(
                R.layout.adapter_gank_content_item, dataList) {
            @Override
            public void onBindView(RVHolder holder, int position, int itemViewType, GankWelfareItem.ResultsEntity item) {
                holder.setTextView(R.id.title, item.getDesc()).setTextView(R.id.date, item.getPublishedAt())
                        .setTextView(R.id.author, item.getWho());
            }

            @Override
            public void onItemClick(RVHolder holder, View view, int position, GankWelfareItem.ResultsEntity item) {
                String linkUrl = item.getUrl();
                if (FilterUtil.isImageUrl(linkUrl)) {
                    PhotoPreview.showPreview(getActivity(), holder.itemView, linkUrl);
                } else {
                    WebBrowser.openUrl(linkUrl);
                }
            }
        });
        return recyclerView;
    }


}
