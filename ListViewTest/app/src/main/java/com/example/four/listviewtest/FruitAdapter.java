package com.example.four.listviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27 0027.
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int itemLayoutId;
    private Context context;

    /* 需要选择一个重载构造函数，（IDE会提示 There is no default constructor in ...）*/
    FruitAdapter(Context context, int itemLayoutId, List<Fruit> objects) {
        super(context, itemLayoutId, objects);
        this.itemLayoutId = itemLayoutId;
        this.context = context;
    }

    /**
     * 当每个子项被滚动到屏幕内的时候调用,返回一个指定位置的子项视图
     * 可以利用传入参数 convertView 进行优化
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Fruit fruit = getItem(position);// 获得当前项的实例（ListView中的一个子项）
        ViewHolder viewHolder;
        View view;
        /* 优化之一 ：避免重复加载已经加载过的子项布局 */
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(itemLayoutId,parent,false);
            System.out.println("getView: 布局：" + view.getClass().getName());
            System.out.println("getView: parent布局：" + parent.getClass().getName());
            viewHolder = new ViewHolder();
            viewHolder.fruitImage =  view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder);
        } else {
            // 如果这个子项布局之前被缓存了（可复用），则直接令view = convertView
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        // 为子项绑定资源、注册监听...
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        return view;
    }

    /* 优化之二 ：内部类缓存子项布局中的控件的引用，不需要都在getView()方法中调用findViewById() */
   static class ViewHolder {
        TextView fruitName;
        ImageView fruitImage;
    }
}

