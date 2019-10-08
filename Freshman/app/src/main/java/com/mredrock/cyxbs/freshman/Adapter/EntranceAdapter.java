package com.mredrock.cyxbs.freshman.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.DensityUtil;
import com.mredrock.cyxbs.freshman.Utility.ImageUtil;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

/**
 * Created by Administrator on 2018/8/16 0016.
 */

public class EntranceAdapter extends RecyclerView.Adapter<EntranceAdapter.ViewHolder> {

    private Context mContext;
    private List<CampusBean.DataBean> mDataList;
    private ImageView titleBackImv;
    private TextView mTitleTv;


    public EntranceAdapter(Context context, List<CampusBean.DataBean> mDataList) {
        this.mContext = context;
        this.mDataList = mDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.freshman_h_entrance_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 先设了两张占位图(画圆角)
        List<String> picUrls = mDataList.get(position).getPictureList();
        ImageUtil.loadImage(mContext, picUrls.get(0), holder.leftImg);
        ImageUtil.loadImage(mContext, picUrls.get(1), holder.rightImg);

        holder.itemMsg.setText(mDataList.get(position).getContent());
        holder.itemName.setText(mDataList.get(position).getName());

        // 将最后一个item的 marginBottom设为 15dp,接口确定后记得修改
        if (position == mDataList.size() - 1) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(DensityUtil.dip2px(mContext, 15), DensityUtil.dip2px(mContext, 15), DensityUtil.dip2px(mContext, 15), DensityUtil
                    .dip2px(mContext, 15));
            holder.view.setLayoutParams(lp);
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ExpandableTextView itemMsg;
        private TextView itemName;
        private ImageView leftImg;
        private ImageView rightImg;
        private View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            itemMsg = itemView.findViewById(R.id.expand_text_view);
            itemName = itemView.findViewById(R.id.item_name);
            leftImg = itemView.findViewById(R.id.item_img_left);
            rightImg = itemView.findViewById(R.id.item_img_right);
        }
    }
}
