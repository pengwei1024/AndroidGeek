package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.app.Global;
import com.apkfuns.androidgank.models.GankWelfareItem;
import com.apkfuns.androidgank.ui.GankIoContent;
import com.apkfuns.androidgank.ui.base.BaseListFragment;
import com.apkfuns.androidgank.utils.JsonHelper;
import com.apkfuns.androidgank.utils.OkHttpClientManager;
import com.apkfuns.simplerecycleradapter.RVHolder;
import com.apkfuns.simplerecycleradapter.SimpleRecyclerAdapter;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class GankIoFragment extends BaseListFragment {

    private int page;
    private GankAdapter adapter;
    private static GankIoFragment fragment;

    public static GankIoFragment getInstance() {
        if (fragment == null) {
            fragment = new GankIoFragment();
        }
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("干货集中营");
        getRecyclerView().setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        onRefresh();
    }

    private void getDataByPage(int page) {
        asyncGet(String.format(Global.GET_GANK_WELFARE, page), 0);
    }

    @Override
    public void onRequestCallBack(int requestCode, String result, boolean success) {
        super.onRequestCallBack(requestCode, result, success);
        Log.d("abcd", "******" + result);
        if (success) {
            GankWelfareItem item = JsonHelper.fromJson(result, GankWelfareItem.class);
            if (notNull(item)) {
                if (notNull(adapter)) {
                    adapter.addAll(item.getResults());
                } else {
                    adapter = new GankAdapter(item.getResults());
                    setAdapter(adapter);
                }
            }
        }
        setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (notNull(adapter)) {
            adapter.clear();
        }
        getDataByPage(page = 1);
    }

    @Override
    public void onLoadMore() {
        getDataByPage(++page);
    }

    class GankAdapter extends SimpleRecyclerAdapter<GankWelfareItem.ResultsEntity> {
        public GankAdapter(List<GankWelfareItem.ResultsEntity> list) {
            super(list);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.adapter_gank_item;
        }

        @Override
        public void onBindView(final RVHolder holder, int position, int itemViewType, GankWelfareItem.ResultsEntity resultsEntity) {
            holder.setTextView(R.id.time, resultsEntity.getPublishedAt().substring(0, 10));
            Glide.with(holder.getContext()).load(resultsEntity.getUrl()).into(holder.getImageView(R.id.imageView));
        }

        @Override
        public void onItemClick(RVHolder holder, View view, int position, GankWelfareItem.ResultsEntity item) {
            holder.startActivity(GankIoContent.class);
        }
    }

}
