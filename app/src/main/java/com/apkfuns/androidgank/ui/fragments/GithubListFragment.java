package com.apkfuns.androidgank.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.WebBrowser;
import com.apkfuns.androidgank.ui.base.BaseListFragment;
import com.apkfuns.logutils.LogUtils;
import com.apkfuns.simplerecycleradapter.RVHolder;
import com.apkfuns.simplerecycleradapter.SimpleRecyclerAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by pengwei on 15/12/3.
 */
public class GitHubListFragment extends BaseListFragment {

    private final static String REQUEST_URL = "request_url";
    private String url;
    private Document doc;

    public static GitHubListFragment getInstance(String url) {
        GitHubListFragment fragment = new GitHubListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(REQUEST_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        url = getArguments().getString(REQUEST_URL);
        new GetData().start();
    }

    class GetData extends Thread {
        @Override
        public void run() {
            try {
                doc = Jsoup.connect(url)
                        .userAgent(getString(R.string.phone_user_agent)).get();
                handler.sendEmptyMessage(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (url.equals("https://github.com/trending?l=java")) {
                Elements elements = doc.getElementsByClass("list-item");
                setAdapter(new SimpleRecyclerAdapter<Element>(R.layout.adapter_github_list, elements) {
                    @Override
                    public void onBindView(RVHolder holder, int position, int itemViewType, Element element) {
                        holder.setTextView(R.id.title, element.getElementsByClass("list-item-title").get(0).html());
                        holder.setTextView(R.id.description, element.getElementsByClass("repo-description").get(0).html());
                        String avatar = element.getElementsByClass("avatar").get(0).attr("src");
                        if (avatar.endsWith("&s=40")) {
                            avatar = avatar.substring(0, avatar.length() - 5);
                        }
                        SimpleDraweeView avatarView = holder.getView(R.id.imageView);
                        avatarView.setImageURI(Uri.parse(avatar));
                        String starNum = element.getElementsByClass("meta").get(0).html();
                        starNum = starNum.substring(0, starNum.indexOf("<span class"));
                        holder.setTextView(R.id.starNum, starNum);
                    }

                    @Override
                    public void onItemClick(RVHolder holder, View view, int position, Element element) {
                        if (notNull(element) && notNull(element.child(0))){
                            WebBrowser.openUrl("https://www.github.com" + element.child(0).attr("href"));
                        }
                    }
                });
            } else if (url.equals("https://github.com/trending/developers?l=java")) {
                Elements elements = doc.getElementsByClass("list-item");
                setAdapter(new SimpleRecyclerAdapter<Element>(R.layout.adapter_github_list, elements) {
                    @Override
                    public void onBindView(RVHolder holder, int position, int itemViewType, Element element) {
                        holder.setTextView(R.id.title, element.getElementsByClass("list-item-title").get(0).html());
                    }
                });
            }
        }
    };


    @Override
    protected boolean onRefreshEnable() {
        return false;
    }

    @Override
    protected boolean onLoadMoreEnable() {
        return false;
    }
}
