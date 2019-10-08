package com.example.four.recyclerviewtest;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2017/10/28 0028.
 */

/* 自定义适配器,继承自RecyclyerView.Adapter,泛型指定为FruitAdapter的内部类ViewAdapter */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> fruitList;
    private int itemLayoutId;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;

        // 内部类ViewHolder的构造函数
        ViewHolder(View view) {
            super(view);
            fruitImage = view.findViewById(R.id.fruit_image);
            fruitName = view.findViewById(R.id.fruit_name);
        }
    }

    FruitAdapter(List<Fruit> fruitList, int itemLayoutId) {
        this.fruitList = fruitList;
        this.itemLayoutId = itemLayoutId;
    }

    private static final String TAG = "FruitAdapter";

    @Override
    /* parent是RecyclerView
       这个方法用于实例化每一个子项的布局，和创建ViewHolder的实例
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "哈哈哈: " + parent.toString());
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
        // flase表示只让我们在父布局中声明的lauout属性生效，但不会为这个view添加父布局，因为view一旦有父布局之后，就不能添加到RecyclerView中了
        final ViewHolder holder = new ViewHolder(view);
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You clicked this image", Toast.LENGTH_SHORT).show();
            }
        });
        holder.fruitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),fruitList.get(holder.getAdapterPosition()).getName() + " position = " +holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    // 这个方法用于对RecyclerView中的子项数据进行赋值，会在每个子项滚动到屏幕内时执行
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = fruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }

    @Override
    //返回数据源的长度
    public int getItemCount() {
        return fruitList.size();
    }
}


//    public final void notifyDataSetChanged();
//
//    /**
//     * 位置的项目已经改变。 相当于调用notifyItemChanged（position，null）;
//     * 这是项目更改事件，而不是结构更改事件。 它表明任何对该位置数据的反映都是过时的，应该更新。 我理解为某一位置的item的更新
//     */
//    public final void notifyItemChanged(int position);
//
//    /**
//     * @param positionStart 已更改的第一个项目的位置
//     * @param itemCount 已更改的项目数
//     * 这是项目更改事件，而不是结构更改事件。 它表明，在给定的位置范围内的数据的任何反映已经过时，应该更新。 我理解为给定范围内items的更新。
//     * 相当于调用notifyItemRangeChanged（position，itemCount，null);
//     */
//    public final void notifyItemRangeChanged(int positionStart, int itemCount);
//
//    /** 插入一个
//     * 通知任何注册的观察者，position位置已被新插入。 原position位置及其之后的item现在位于位置+1。
//     * 这是一个结构性变化事件(插入)。 数据源中其他现有item(s)的信息仍然被认为是最新的，不会和数据源重新绑定，虽然它们的位置可能会改变。
//     */
//    public final void notifyItemInserted(int position);
//
//
//    public final void notifyItemRangeInserted(int positionStart, int itemCount);
//
//    /**
//     * @param fromPosition
//     * @param toPosition
//     */
//    public final void notifyItemMoved(int fromPosition, int toPosition);
//
//
//    /** 批量删除
//     * @param position 已被删除的项目的位置
//     * 通知任何注册的观察者，position位置的item已被删除。 原position位置及其之后的item现在位于位置-1。
//     * 这是一个结构性变化事件(删除)。 数据源中其他现有item(s)的信息仍然被认为是最新的，不会和数据源重新绑定，虽然它们的位置可能会改变。
//     */
//    public final void notifyItemRemoved(int position);
//
//    /** 删除一个
//     * @param positionStart 开始删除的位置
//     * @param itemCount 删除的item数
//     * 通知任何注册的观察者，以前位于[positionStart,positionStart+itemCount)的item已经从数据集中移除。 之前位于positionStart + itemCount后面的item,
// 现在的位置是oldPosition - itemCount。
//     * 这是一个结构性变化事件。 数据集中其他现有item(s)的信息仍然被认为是最新的，不会和数据源重新绑定，虽然它们的位置可能会改变。
//     */
//    public final void notifyItemRangeRemoved(int positionStart, int itemCount);
