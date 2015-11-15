package com.apkfuns.androidgank.app;

import android.app.Application;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class App extends Application {

    private static App single;

    @Override
    public void onCreate() {
        super.onCreate();
        single = this;
    }

    public static App get(){
        return single;
    }
}
