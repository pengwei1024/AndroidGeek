package com.apkfuns.androidgank.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.apkfuns.androidgank.R;
import com.apkfuns.simplerecycleradapter.SimpleRecyclerAdapter;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class BaseListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    private int lastVisibleItemPosition;
    private int[] lastPositions;
    protected View rootView;
    private int currentScrollState = 0;

    public int getContentLayoutId() {
        return R.layout.view_recycler_view;
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentLayoutId(), container, false);
        ensureView();
        return rootView;
    }

    private void ensureView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        if (notNull(mSwipeRefreshLayout)) {
            if (onRefreshEnable()) {
                mSwipeRefreshLayout.setOnRefreshListener(this);
            } else {
                mSwipeRefreshLayout.setEnabled(false);
            }
        }
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (onLoadMoreEnable()) {
            mRecyclerView.addOnScrollListener(getScrollToBottomListener());
        }
        mRecyclerView.setHasFixedSize(true);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    /**
     * 设置adapter
     *
     * @param adapter
     */
    protected void setAdapter(RecyclerView.Adapter adapter) {
        if (notNull(mRecyclerView) && notNull(adapter)) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    protected SimpleRecyclerAdapter getAdapter() {
        if (notNull(mRecyclerView) && notNull(mRecyclerView.getAdapter())) {
            return (SimpleRecyclerAdapter) mRecyclerView.getAdapter();
        }
        return null;
    }

    /**
     * 实现显示滑动进度
     *
     * @param requestDataRefresh
     */
    public void setRefreshing(boolean requestDataRefresh) {
        if (notNull(mSwipeRefreshLayout)) {
            if (requestDataRefresh) {
                mSwipeRefreshLayout.setRefreshing(true);
            } else {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        }
    }

    protected RecyclerView.OnScrollListener getScrollToBottomListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                } else if (layoutManager instanceof GridLayoutManager) {
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager
                            = (StaggeredGridLayoutManager) layoutManager;
                    if (lastPositions == null) {
                        lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    }
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findMax(lastPositions);
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                currentScrollState = newState;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                        (lastVisibleItemPosition) >= totalItemCount - 1)) {
                    setRefreshing(true);
                    onLoadMore();
                }
            }
        };
    }

    /**
     * 滑动监听
     *
     * @param listener
     */
    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mRecyclerView.addOnScrollListener(listener);
    }

    @Override
    public void onRefresh() {

    }

    /**
     * 加载更多
     */
    public void onLoadMore() {

    }

    /**
     * 是否启用下拉刷新
     *
     * @return
     */
    protected boolean onRefreshEnable() {
        return true;
    }

    /**
     * 是否启用加载更多
     *
     * @return
     */
    protected boolean onLoadMoreEnable() {
        return true;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


}
