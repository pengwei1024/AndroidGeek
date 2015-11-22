package com.apkfuns.androidgank.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Created by pengwei08 on 15/10/22.
 */
public class PhotoPreview extends BaseActivity {
    private SimpleDraweeView mSimpleDraweeView;
    public static final String TRANSIT_PIC = "picture";
    public String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopreview);
        mSimpleDraweeView = findView(R.id.image);
        ViewCompat.setTransitionName(mSimpleDraweeView, TRANSIT_PIC);
        imageUrl = getIntent().getStringExtra(TRANSIT_PIC);
        mSimpleDraweeView.setImageURI(Uri.parse(imageUrl));
    }

    @Override
    protected boolean showToolBar() {
        return false;
    }

    /**
     * 展示图片
     *
     * @param activity
     * @param view
     * @param imageUrl
     */
    public static void showPreview(Activity activity, View view, String imageUrl) {
        Intent intent = new Intent(activity, PhotoPreview.class);
        intent.putExtra(TRANSIT_PIC, imageUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view,
                        PhotoPreview.TRANSIT_PIC);
        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }
}
