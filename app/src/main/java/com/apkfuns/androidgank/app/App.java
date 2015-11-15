package com.apkfuns.androidgank.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class App extends Application {

    private static App single;

    @Override
    public void onCreate() {
        super.onCreate();
        single = this;

        // 配置Fresco
        ImagePipelineConfig frescoConfig = OkHttpImagePipelineConfigFactory.newBuilder(this, getHttpClient())
                .build();
        Fresco.initialize(this, frescoConfig);
    }

    public static App get(){
        return single;
    }

    /**
     * 获取httpclient对象
     *
     * @return
     */
    public static OkHttpClient getHttpClient() {
        return new OkHttpClient();
    }
}
