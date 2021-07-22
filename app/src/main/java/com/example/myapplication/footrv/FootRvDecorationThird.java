package com.example.myapplication.footrv;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FootRvDecorationThird extends RecyclerView.ItemDecoration {

    private final List<StickyViewHolder> viewHolderList;
    private final LinearLayout stickyView;

    public FootRvDecorationThird(@NonNull LinearLayout stickyView, List<StickyViewHolder> viewHolderList) {
        this.stickyView = stickyView;
        this.viewHolderList = viewHolderList;
    }

    boolean draw = false;

    @Override
    public void onDrawOver(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        Log.i("zyc", "onDrawOver.");
        if (isIotHeaderOverTop(parent) && !draw) {
            draw = true;
            stickyView.removeAllViews();
            Log.i("zyc", "viewHolderList:" + viewHolderList);
            for (StickyViewHolder viewHolder : viewHolderList) {
                View targetView = LayoutInflater.from(parent.getContext()).inflate(viewHolder.layoutId(), parent, false);
                stickyView.addView(targetView);
            }
            stickyView.setVisibility(isIotHeaderOverTop(parent) ? View.VISIBLE : View.GONE);
        }
        if (!isIotHeaderOverTop(parent) && draw) {
            draw = false;
            stickyView.setVisibility(isIotHeaderOverTop(parent) ? View.VISIBLE : View.GONE);
        }
    }

    public void changed(){
        draw = false;
    }

    private boolean isIotHeaderOverTop(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() > 1;
        }
        return false;
    }


}
