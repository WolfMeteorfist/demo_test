package com.example.myapplication.slidingup;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivitySlidingUpBinding;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * @author yuechou.zhang
 * @since 2021/7/12
 */
public class SlidingUpPanelActivity extends AppCompatActivity {

    private ActivitySlidingUpBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySlidingUpBinding.inflate(getLayoutInflater());
        binding.btnFade.setOnClickListener(v->{
            binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        });
        setContentView(binding.getRoot());
    }
}
