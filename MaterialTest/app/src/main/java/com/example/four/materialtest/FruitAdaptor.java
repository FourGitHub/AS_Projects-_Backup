package com.example.four.materialtest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2018/1/13 0013.
 */

public class FruitAdaptor extends RecyclerView.Adapter<FruitAdaptor.ViewHolder> {
    private List<Fruit> mFruitList;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getFruitName());
                mContext.startActivity(intent);
            }
        });

        holder.fruitImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("确认从列表中删除该图片吗？")
                        .setIcon(R.drawable.icon_warning)
                        .setCancelable(false)
                        .setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeItem(holder.getAdapterPosition());
                            }
                        })
                        .setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getFruitName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }


    public FruitAdaptor(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView fruitItem;
        private ImageView fruitImage;
        private TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            fruitItem = (CardView) view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }

    }

    public void removeItem(int position) {
        mFruitList.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mFruitList.size());
//        notifyDataSetChanged();// 这个方法非常重要
    }

}
