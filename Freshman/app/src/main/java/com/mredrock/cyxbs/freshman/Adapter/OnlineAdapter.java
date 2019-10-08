package com.mredrock.cyxbs.freshman.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.DataBean.OnlineBean;
import com.mredrock.cyxbs.freshman.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/16 0016.
 */
@SuppressLint("SetTextI18n")
public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.ViewHolder> {


    private Context mContext;
    private List<OnlineBean.GroupBean> mResultLists;

    public OnlineAdapter(Context context) {
        this.mContext = context;
        mResultLists = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.freahman_h_search_result_item, parent, false);
        return new ViewHolder(item);
    }

    public void updateData(List<OnlineBean.GroupBean> dataList) {
        mResultLists.clear();
        mResultLists.addAll(dataList);
        notifyDataSetChanged();
    }

    // 这里就是数据绑定了
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OnlineBean.GroupBean bean = mResultLists.get(position);
        holder.itemName.setText(bean.getAreaName() + "   :  " + bean.getGroupNumberList().get(0).getGroupNumber());
    }

    @Override
    public int getItemCount() {
        return mResultLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;

        ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name_tv);
        }
    }
}
