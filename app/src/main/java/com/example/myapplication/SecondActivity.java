package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author yuechou.zhang
 * @since 2021/6/15
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.textView2).setOnClickListener((v) -> {
            Utils.showToast("TV Coming");
        });
        findViewById(R.id.button2).setOnClickListener(v -> {
            Utils.showToast("Btn Coming.");
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        findViewById(R.id.second_container).dispatchTouchEvent(ev);
        return false;
    }
}
