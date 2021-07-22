package com.example.myapplication.slidingup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySlideUpBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author yuechou.zhang
 * @since 2021/7/12
 */
public class SlideUpActivity extends AppCompatActivity {

    private ActivitySlideUpBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySlideUpBinding.inflate(getLayoutInflater());
        binding.rvSlideUp.setAdapter(new MyAdapter());
        setContentView(binding.getRoot());
        initVp2();
    }

    private void initVp2() {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter();
        binding.vpSlideUp.setAdapter(adapter);
    }

    class MyViewPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @NonNull
        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vp2_item, parent, false);
                return new ViewHolder(view);
            }else{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vp2_item_two, parent, false);
                return new RecyclerView.ViewHolder(view){

                };
            }
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                RecyclerView recyclerView = itemView.findViewById(R.id.rv_vp2_item);
                recyclerView.setAdapter(new MyAdapter());
                Log.i("zyc", "创建了RecyclerView:" + recyclerView);
            }

        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @NonNull
        @NotNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
            holder.setTextName("position: " + position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private final TextView tv;
            private final Button btn;

            public MyViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                btn = itemView.findViewById(R.id.btn);
            }

            public void setTextName(String name) {
                tv.setText(name);
                btn.setOnClickListener(v -> {
                    Toast.makeText(SlideUpActivity.this, "Toast.", Toast.LENGTH_SHORT).show();
                    if (binding.slidingLayout.isCollapsed()) {
                        binding.slidingLayout.setStatus(NewSlideUpView.STATUS_EXPAND);
                    } else {
                        binding.slidingLayout.setStatus(NewSlideUpView.STATUS_COLLAPSED);
                    }
                });
            }
        }
    }
}
