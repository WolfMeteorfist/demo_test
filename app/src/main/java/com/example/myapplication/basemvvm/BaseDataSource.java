package com.example.myapplication.basemvvm;

import android.app.Application;
import android.content.Context;

import androidx.core.app.ActivityCompat;

import com.example.myapplication.Utils;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public abstract class BaseDataSource implements IDataSource {

    private final Context context;

    public BaseDataSource(Context context) {
        this.context = context;
    }

    public BaseDataSource() {
        context = Utils.getApplicationContext();
    }


    /**
     * 可以用于初始化一些请求helper/或者retrofit创建等
     */
    @Override
    public void init() {

    }

    @Override
    public void release() {

    }

}
