package com.example.myapplication.basemvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public abstract class BaseViewModel<D extends IDataSource> extends AndroidViewModel implements LifecycleObserver {

    protected D dataSource;

    public BaseViewModel(@NonNull @NotNull Application application) {
        super(application);
        Class<D> dataSourceClass = getDataSourceClass();
        try {
            Constructor<D> constructor = dataSourceClass.getConstructor(Context.class);
            if (constructor == null) {
                Log.i("zyc", "constructor为空");
                return;
            }
            dataSource = constructor.newInstance(application);
        } catch (Exception e) {
            Log.i("zyc", "error happened. e: " + e.getMessage());
            try {
                dataSource = dataSourceClass.newInstance();
            } catch (Exception otherE) {
                otherE.printStackTrace();
            }
        }
    }

    /**
     * 初始化
     */
    void onAttachToActivity() {
        if (dataSource != null) {
            dataSource.init();
        }
    }

    @Override
    protected void onCleared() {
        if (dataSource != null) {
            dataSource.release();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {

    }

    void onResume() {

    }

    void onStop() {

    }

    void onPause() {

    }

    private Class<D> getDataSourceClass() {
        Type type = getClass().getGenericSuperclass();
        if (type == null) {
            return null;
        }
        if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            return ((Class) types[0]);
        }
        return null;
    }

}
