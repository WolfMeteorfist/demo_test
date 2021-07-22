package com.example.myapplication.slidingup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * @author yuechou.zhang
 * @since 2021/7/14
 */
public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(@NonNull @NotNull Context context) {
        super(context);
    }

    public MyRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("zyc", "recyclerview dispatchTouchEvent ev:" + ev.getActionMasked());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Log.i("zyc", "recyclerview onInterceptTouchEvent ev:" + e.getActionMasked());
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.i("zyc", "recyclerview onTouchEvent e:" + e.getActionMasked());
        return super.onTouchEvent(e);
    }
}
