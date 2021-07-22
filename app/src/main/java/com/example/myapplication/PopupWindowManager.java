package com.example.myapplication;

import android.widget.PopupWindow;

/**
 * @author yuechou.zhang
 * @since 2021/7/9
 */
public class PopupWindowManager {

    static PopupWindow popupWindow;

    public static void init(){
        popupWindow = new PopupWindow();
        //popupWindow.setContentView(R.layout);
    }

}
