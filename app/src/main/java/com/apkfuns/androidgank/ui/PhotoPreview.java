package com.apkfuns.androidgank.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.apkfuns.androidgank.R;
import com.apkfuns.androidgank.ui.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by pengwei08 on 15/10/22.
 */
public class PhotoPreview extends BaseActivity {
    private ImageView mImageView;
    public static final String TRANSIT_PIC = "picture";
    public String imageUrl;
    private PhotoViewAttacher attacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopreview);
        mImageView = findView(R.id.image);
        ViewCompat.setTransitionName(mImageView, TRANSIT_PIC);
        imageUrl = getIntent().getStringExtra(TRANSIT_PIC);
        attacher = new PhotoViewAttacher(mImageView);
        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                onBackPressed();
            }
        });
        Glide.with(this).load(imageUrl).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                mImageView.setImageDrawable(resource);
                attacher.update();
            }
        });
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
