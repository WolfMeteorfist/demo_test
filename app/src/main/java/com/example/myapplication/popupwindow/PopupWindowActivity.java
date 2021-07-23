package com.example.myapplication.popupwindow;

import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityPopupwindowBinding;

/**
 * @author yuechou.zhang
 * @since 2021/7/23
 */
public class PopupWindowActivity extends AppCompatActivity {

    private PopupWindow popupWindow;
    private ActivityPopupwindowBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_popupwindow);
        binding.btnShowPopup.setOnClickListener(this::showPopupWindow);
    }

    private void showPopupWindow(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_room, null);
        view.findViewById(R.id.tv_gg).setSelected(true);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Slide slide = new Slide(Gravity.BOTTOM);
            popupWindow.setEnterTransition(slide);
            popupWindow.setExitTransition(slide);
        }
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
}
