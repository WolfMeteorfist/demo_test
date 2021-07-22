package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public class Utils {

    private static Context applicationContext;

    public static void init(Context context) {
        applicationContext = context;
    }

    public static Context getApplicationContext(){
        return applicationContext;
    }

    public static void showToast(String msg) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show();
    }

}
