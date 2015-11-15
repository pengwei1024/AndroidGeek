package com.apkfuns.androidgank.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.androidgank.R;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class BaseListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    // 最后可见的item位置
    private int lastVisibleItem;
    protected View rootView;

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
        if (onRefreshEnable()) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
        } else {
            mSwipeRefreshLayout.setEnabled(false);
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
                if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
//                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerView.getAdapter().getItemCount()) {
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


}
