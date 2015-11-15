package com.apkfuns.androidgank.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.app.Global;
import com.apkfuns.androidgank.models.GankWelfareItem;
import com.apkfuns.androidgank.ui.base.BaseActivity;
import com.apkfuns.androidgank.ui.base.BaseFragment;
import com.apkfuns.androidgank.ui.base.BaseTabActivity;
import com.apkfuns.androidgank.ui.fragments.GankContentItemFragment;
import com.apkfuns.androidgank.ui.fragments.GankIoFragment;
import com.apkfuns.androidgank.utils.JsonHelper;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.umeng.comm.ui.dialogs.ImageBrowser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pengwei08 on 15/11/15.
 */
public class GankIoContent extends BaseTabActivity implements View.OnClickListener {

    private String date;
    private String imageUrl;
    private NestedScrollView nestedScrollView;

    @Override
    protected int getContentView() {
        return R.layout.activity_gank_content;
    }

    @Override
    protected void initTab() {
        nestedScrollView = findView(R.id.nestedScrollView);
        nestedScrollView.setFillViewport(true);
        date = getIntent().getStringExtra("date");
        if (date.length() == 10) {
            String[] dateParam = date.split("-");
            asyncGet(String.format(Global.GET_GANK_CONTENT_BY_DAY,
                    dateParam[0], dateParam[1], dateParam[2]), 0);
        }
        imageUrl = getIntent().getStringExtra("imageUrl");
        SimpleDraweeView simpleDraweeView = findView(R.id.backdrop);
        simpleDraweeView.setImageURI(Uri.parse(imageUrl));
        CollapsingToolbarLayout collapsingToolbar = findView(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(date);
    }

    @Override
    public void onRequestCallBack(int requestCode, String result, boolean success) {
        if (success) {
            try {
                JSONObject root = new JSONObject(result);
                if (!root.getBoolean("error")) {
                    JSONObject results = root.getJSONObject("results");
                    JSONArray categoryArray = root.getJSONArray("category");
                    for (int i = 0; i < categoryArray.length(); ++i) {
                        String categoryName = categoryArray.getString(i);
                        ArrayList<GankWelfareItem.ResultsEntity> lists =
                                JsonHelper.getInstance().fromJson(results.getString(categoryName),
                                        new TypeToken<ArrayList<GankWelfareItem.ResultsEntity>>() {
                                        }.getType());
                        addTitleAndFragment(categoryName, GankContentItemFragment.getInstance(lists));
                    }
                    tabShow();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onRequestCallBack(requestCode, result, success);
    }

    @Override
    public void onClick(View v) {

    }
}
