package com.mredrock.cyxbs.freshman.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.DataBean.SaveBean.DescribeBean;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.UI.Activity.RuXueActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2018/8/9 0009.
 */


// 有一个想法： 当用户退出当前Activity的时候，就把现在最新的 mDataList 返回，
// 以itemName为查询条件，更新数据库中对应信息。

public class RuXueAdapter extends RecyclerView.Adapter<RuXueAdapter.ViewHolder> {

    private static final String TAG = "RuXueAdapter";
    private Context context;

    //消息列表数据
    private List<DescribeBean> mDataList;

    // 当前列表是否在编辑状态
    private boolean isEditing = false;

    // 编辑状态下，已选将要删除的item数量，用于返回给title显示
    private int delNumber = 0;


    // 编辑状态下，用一个数组保存将要删除的item的position,记得初始化！
    private HashSet<Integer> delPositions;


    // 记录当前已经完成的最后一个item的position,当继需勾选已完成时，用于这个被勾选item的插入(上浮)位置
    private int lastHaveDoneItemPosition = 0;

    public RuXueAdapter(Context context) {
        delPositions = new HashSet<>();
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView rexueItemMoreIV;
        private TextView ruxueItemNameTv;
        private TextView ruxueItemMoreTv;
        private ImageView ruxueItemCheckImv;
        private LinearLayout ruxueItemMoreLayout;


        ViewHolder(View itemView) {
            super(itemView);
            rexueItemMoreIV = itemView.findViewById(R.id.ruxue_item_more_iv);
            ruxueItemNameTv = itemView.findViewById(R.id.ruxue_item_name_tv);
            ruxueItemMoreTv = itemView.findViewById(R.id.ruxue_item_dowm_more_tv);
            ruxueItemCheckImv = itemView.findViewById(R.id.ruxue_item_check_igv);
            ruxueItemMoreLayout = itemView.findViewById(R.id.ruxue_item_more_layout);

            rexueItemMoreIV.setOnClickListener(v -> {
                int nowClickItemPosition = getAdapterPosition();
                if (mDataList.get(nowClickItemPosition).isOpen()) {
                    //当点击的item已经被展开了, 就关闭.
                    mDataList.get(nowClickItemPosition).setOpen(false);
                } else {
                    mDataList.get(nowClickItemPosition).setOpen(true);
                }
                notifyItemChanged(nowClickItemPosition);
            });

            ruxueItemCheckImv.setOnClickListener(v -> {
                if (!isEditing) {
                    if (mDataList.get(getAdapterPosition()).isChecked()) {
                        int position = getAdapterPosition();// 一定一定先保存好位置，否则remove后，再调用getAdapterPosition()会返回-1
                        DescribeBean insertItem = mDataList.get(position);
                        notifyItemRemoved(position);
                        lastHaveDoneItemPosition--;
                        insertItem.setChecked(false);
                        mDataList.remove(position);
                        mDataList.add(lastHaveDoneItemPosition, insertItem);
                        notifyItemInserted(lastHaveDoneItemPosition);
                    } else {
                        int position = getAdapterPosition();
                        DescribeBean insertItem = mDataList.get(position);
                        notifyItemRemoved(position);
                        insertItem.setChecked(true);
                        mDataList.remove(position);
                        mDataList.add(lastHaveDoneItemPosition, insertItem);
                        notifyItemInserted(lastHaveDoneItemPosition);
                        lastHaveDoneItemPosition++;
                        if (lastHaveDoneItemPosition == 1) {
                            notifyDataSetChanged();
                        }
                    }
                } else {
                    // 如果编辑状态下，点击的之前已经是选中待删除状态，则点击表示取消待删除
                    int position = getAdapterPosition();
                    if (mDataList.get(position).isWillBeDelete()) {
                        mDataList.get(position).setWillBeDelete(false);
                        delNumber--;
                        delPositions.remove(position);
                        Log.w(TAG, "-->> delPositions.remove" + position);
                        ((RuXueActivity) context).setDelTv(delNumber);
                        notifyItemChanged(position);
                    } else {
                        mDataList.get(position).setWillBeDelete(true);
                        delNumber++;
                        delPositions.add(position);
                        Log.w(TAG, "-->> delPositions.add" + position);
                        ((RuXueActivity) context).setDelTv(delNumber);
                        notifyItemChanged(position);
                    }
                }
            });
        }

        /**
         * 此方法实现列表数据的绑定和item的展开/关闭
         */
        void bindView(int pos, DescribeBean bean) {
//            TextPaint tp = ruxueItemNameTv.getPaint();
//            tp.setFakeBoldText(true);
            ruxueItemNameTv.setText(bean.getName());
            ruxueItemMoreTv.setText(bean.getContent());
            if (bean.getName().startsWith("《")) {
                ruxueItemNameTv.setPadding(-13, 0, 0, 0);
            } else ruxueItemNameTv.setPadding(0, 0, 0, 0);


            if (bean.isCustom()) {
                ruxueItemMoreLayout.setVisibility(View.GONE);
                rexueItemMoreIV.setVisibility(View.GONE);
            } else {
                if (bean.isOpen()) {
                    ruxueItemMoreLayout.setVisibility(View.VISIBLE);
                    rexueItemMoreIV.setImageResource(R.drawable.freshman_h_ruxue_up);
                } else {
                    ruxueItemMoreLayout.setVisibility(View.GONE);
                    rexueItemMoreIV.setImageResource(R.drawable.freshman_h_ruxue_down);
                }
            }

            // 非编辑状态下：
            if (!isEditing) {
                if (bean.isChecked()) {
                    ruxueItemCheckImv.setImageResource(R.drawable.freshman_h_ruxue_checked);
                    ruxueItemNameTv.setTextColor(Color.parseColor("#999999"));
                    ruxueItemCheckImv.setVisibility(View.VISIBLE);
                } else {
                    ruxueItemCheckImv.setImageResource(R.drawable.freshman_h_ruxue_uncheck);
                    ruxueItemNameTv.setTextColor(Color.parseColor("#333333"));
                    ruxueItemCheckImv.setVisibility(View.VISIBLE);
                }
            }

            // 编辑状态下：
            if (isEditing) {
                ruxueItemNameTv.setTextColor(Color.parseColor("#333333"));
                if (!"必需".equals(bean.getProperty())) {
                    if (bean.isWillBeDelete()) {
                        ruxueItemCheckImv.setImageResource(R.drawable.freshman_h_will_del);
                        Log.w(TAG, "-->> delNumber = " + delNumber);
                    } else {
                        ruxueItemCheckImv.setImageResource(R.drawable.freshman_h_not_del);
                    }
                } else {
                    ruxueItemCheckImv.setVisibility(View.GONE);
                }
            }
        }

    }

    /**
     * 设置列表数据
     *
     * @param mDataList
     */
    public void setDataList(List<DescribeBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public void addNewItem(DescribeBean bean) {
        mDataList.add(bean);
        notifyItemInserted(mDataList.size() - 1);
    }

    public List<DescribeBean> getDataList() {
        return mDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.freshman_h_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindView(position, mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void editMode(boolean editing) {
        isEditing = editing;
        if (isEditing) {
            for (int i = 0; i < getDataList().size(); i++) {
                mDataList.get(i).setOpen(false);
            }
        } else {
            ArrayList<Integer> delPosition = new ArrayList<>(delPositions);
            Collections.sort(delPosition); // 先排序，从position标号最大的位置开始删
            int i = delPosition.size();
            while (i > 0) {
                // 我擦，这个intValue()真的很重要！！！
                mDataList.remove(delPosition.get(i - 1).intValue());
                i--;
            }
            for (int j = 0; j < getDataList().size(); j++) {
                mDataList.get(j).setOpen(false);
            }

            // 这两个东西也要置位。
            delNumber = 0;
            delPositions.clear();
        }

        notifyDataSetChanged();
    }

    public void cancelEdit() {
        isEditing = false;
        for (DescribeBean b :
                mDataList) {
            b.setOpen(false);
            b.setWillBeDelete(false);
        }
        delNumber = 0;
        delPositions.clear();
        notifyDataSetChanged();
    }

    public int getDelNumber() {
        return delNumber;
    }

    public void setDelNumber(int delNumber) {
        this.delNumber = delNumber;
    }

    public int getLastHaveDoneItemPosition() {
        return lastHaveDoneItemPosition;
    }

    public void setLastHaveDoneItemPosition(int lastHaveDoneItemPosition) {
        this.lastHaveDoneItemPosition = lastHaveDoneItemPosition;
    }

}
