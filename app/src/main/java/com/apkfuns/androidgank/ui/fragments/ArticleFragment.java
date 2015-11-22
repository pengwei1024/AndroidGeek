package com.apkfuns.androidgank.ui.fragments;

import android.os.Bundle;
import android.view.View;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.network.AVQuery;
import com.apkfuns.androidgank.network.ArticleObject;
import com.apkfuns.androidgank.ui.WebBrowser;
import com.apkfuns.androidgank.ui.base.BaseListFragment;
import com.apkfuns.simplerecycleradapter.RVHolder;
import com.apkfuns.simplerecycleradapter.SimpleRecyclerAdapter;

import java.util.List;

/**
 * Created by pengwei on 15/11/21.
 */
public class ArticleFragment extends BaseListFragment {

    public static ArticleFragment getInstance() {
        return new ArticleFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData(null);
    }

    private void getData(String lastTime) {
        AVQuery.GetArticle(new AVQuery.AvCallBack<ArticleObject>() {
            @Override
            public void onResult(final List<ArticleObject> list, Exception e) {
                if (notNull(list)) {
                    if (notNull(getAdapter())) {
                        getAdapter().addAll(list);
                    } else {
                        setAdapter(new SimpleRecyclerAdapter<ArticleObject>(R.layout.adapter_article_item, list) {
                            @Override
                            public void onBindView(RVHolder holder, int position, int itemViewType, ArticleObject article) {
                                holder.setTextView(R.id.title, article.getTitle()).setTextView(R.id.author, article.getAuthor())
                                        .setTextView(R.id.time, article.getCreatedAt());
                            }

                            @Override
                            public void onItemClick(RVHolder holder, View view, int position, ArticleObject item) {
                                WebBrowser.openUrl(item.getUrl());
                            }
                        });
                    }
                }
            }
        }, lastTime);
    }
}