package com.example.myapplication;

import android.app.Application;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
