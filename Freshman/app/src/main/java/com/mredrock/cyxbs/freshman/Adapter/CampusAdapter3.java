package com.mredrock.cyxbs.freshman.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.UI.Activity.DataActivity;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class CampusAdapter3 extends RecyclerView.Adapter<CampusAdapter3.ViewHolder> {
    private Context mContext;

    // 比如这是银行这个Frg的数据，数据库中也只可能有一项
    private String[] mCampusBeanList = new String[]{"通信与信息工程学院",
            "传媒艺术学院",
            "经济管理学院/现代邮政学院",
            "软件工程学院",
            "自动化学院",
            "光电工程学院/重庆国际半导体学院",
            "计算机科学与技术学院",
            "生物信息学院",
            "理学院",
            "网络安全与信息法学院",
            "体育学院",
            "先进制造工程学院",
            "外国语学院",
            "国际学院"};

    public CampusAdapter3(Context context) {
        this.mContext = context;
    }

    @Override
    public CampusAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.freshman_h_campus_item_3, parent, false);
        return new CampusAdapter3.ViewHolder(view);
    }

    /*
    每个item被滚动到屏幕内时将会调用此方法绑定
    这个方法还要完善
    1.右箭头的点击事件跳转显示柱状图的Activity
    2. holder.itemName.setText()
    */
    @Override
    public void onBindViewHolder(CampusAdapter3.ViewHolder holder, int position) {

        holder.itemName.setText(mCampusBeanList[position]);

        holder.baseView.setOnClickListener(v-> DataActivity.actionStart(mContext,mCampusBeanList[position]));
    }

    @Override
    public int getItemCount() {
        return mCampusBeanList.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private ImageView itemImg;

        private View baseView;

        public ViewHolder(View itemView) {
            super(itemView);
            baseView = itemView;
            itemName = itemView.findViewById(R.id.item_name_tv);
            itemImg = itemView.findViewById(R.id.item_imv);
        }
    }

}

