package com.mredrock.cyxbs.freshman.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.Const;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15 0015.
 * 这个item大概就是一张银行照片，银行名字，银行描述
 */

public class CampusAdapter2 extends RecyclerView.Adapter<CampusAdapter2.ViewHolder> {
    private Context mContext;

    // 比如这是银行这个Frg的数据，数据库中也只可能有一项
    private List<CampusBean.DataBean> mCampusBeanList;

    public CampusAdapter2(Context context, List<CampusBean.DataBean> campusBeanList) {
        this.mContext = context;
        this.mCampusBeanList = campusBeanList;
    }

    @Override
    public CampusAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.freshman_h_campus_item_2, parent, false);
        return new CampusAdapter2.ViewHolder(view);
    }

    /*
    每个item被滚动到屏幕内时将会调用此方法绑定
    */
    @Override
    public void onBindViewHolder(CampusAdapter2.ViewHolder holder, int position) {
        //        还没有数据，就先写在这里！
        holder.itemMsg.setText(mCampusBeanList.get(position).getContent());
        holder.itemName.setText(mCampusBeanList.get(position).getName());
        String suffix = mCampusBeanList.get(position).getPictureList().size() > 0 ?
                mCampusBeanList.get(position).getPictureList().get(0) : new String("");
        Glide.with(mContext).load(Const.IMG_PREFIX + suffix)
                .placeholder(R.drawable.freshman_h_zhanwei) // 占位图
                .error(R.drawable.freshman_h_zhanwei) // 错误占位图
                .diskCacheStrategy(DiskCacheStrategy.RESULT) // 缓存策略
                .into(holder.itemImg);
//        if (mCampusBeanList.get(position).getPictureList().size() > 0)
//            Glide.with(mContext).load(Const.IMG_PREFIX + mCampusBeanList.get(position).getPictureList().get(0)).into(holder.itemImg);
    }

    @Override
    public int getItemCount() {

        //return 4; ///////// 测试用假设有4个item
        return mCampusBeanList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImg;
        private TextView itemName;
        private TextView itemMsg;


        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name_tv);
            itemMsg = itemView.findViewById(R.id.item_msg_tv);
            itemImg = itemView.findViewById(R.id.item_msg_img);
        }
    }

}

