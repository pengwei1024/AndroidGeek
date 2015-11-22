package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.network.AVQuery;
import com.apkfuns.androidgank.network.BlogObject;
import com.apkfuns.androidgank.ui.WebBrowser;
import com.apkfuns.androidgank.ui.base.BaseListFragment;
import com.apkfuns.simplerecycleradapter.RVHolder;
import com.apkfuns.simplerecycleradapter.SimpleRecyclerAdapter;

import java.util.List;

/**
 * Created by pengwei on 15/11/22.
 * 博主
 */
public class CodeAuthorFragment extends BaseListFragment {

    public static CodeAuthorFragment getInstance(String type) {
        CodeAuthorFragment fragment = new CodeAuthorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AVQuery.GetBlog(getArguments().getString("type"), new AVQuery.AvCallBack() {
            @Override
            public void onResult(List list, Exception e) {
                if (notNull(list)) {
                    setAdapter(new SimpleRecyclerAdapter<BlogObject>(R.layout.adapter_article_item, list) {
                        @Override
                        public void onBindView(RVHolder holder, int position, int itemViewType, BlogObject item) {
                            holder.setTextView(R.id.title, item.getName());
                        }

                        @Override
                        public void onItemClick(RVHolder holder, View view, int position, BlogObject item) {
                            WebBrowser.openUrl(item.getUrl());
                        }
                    });
                }
            }
        });
    }
}
