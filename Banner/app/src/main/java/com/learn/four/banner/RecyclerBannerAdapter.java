package com.learn.four.banner;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create on 2019/07/21
 *
 * @author Four
 * @description
 */
public class RecyclerBannerAdapter extends RecyclerView.Adapter<RecyclerBannerAdapter.ViewHolder> {

    private List<Integer> colors = new ArrayList<>(4);
    public static final int pageCount = 4;

    public RecyclerBannerAdapter() {
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
    } // 初始化 colors

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recycler_page_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pageLayout.setCardBackgroundColor(colors.get(position % pageCount));
        holder.pageTv.setText("Page " + position % pageCount);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.page_tv)
        TextView pageTv;
        @BindView(R.id.page_layout)
        CardView pageLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
