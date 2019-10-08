package com.mredrock.cyxbs.freshman.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mredrock.cyxbs.freshman.CustomView.CircleViewPager;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.Const;
import com.mredrock.cyxbs.freshman.Utility.DensityUtil;
import com.mredrock.cyxbs.freshman.Utility.ImageUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/8/15 0015.
 * 这个是大型活动Fragment的RecyclerView适配器，每个item就三项： 图片、名字、描述
 * （校运会/大型活动）
 */

public class DXHDRecyclerAdapter extends RecyclerView.Adapter<DXHDRecyclerAdapter.ViewHolder> {
    private Context mContext;

    private List<CampusBean.DataBean> mDataBeanList;

    public DXHDRecyclerAdapter(Context context, List<CampusBean.DataBean> mDataBeanList) {
        this.mContext = context;
        this.mDataBeanList = mDataBeanList;
    }

    @Override
    public DXHDRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.freshman_h_dxhd_item, parent, false);
        return new DXHDRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DXHDRecyclerAdapter.ViewHolder holder, int position) {
        CampusBean.DataBean dataBean = mDataBeanList.get(position);
        int picNums = dataBean.getPictureList().size();
        holder.itemName.setText(dataBean.getName());
        holder.itemMsg.setText(dataBean.getContent());
        // 添加小圆点
        for (int i = 0; i < picNums; i++) {
            ImageView dotView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 16;
            params.rightMargin = 16;
            dotView.setLayoutParams(params);
            holder.dotsLayout.addView(dotView);
            holder.dotViewsList.add(dotView);
        }

        // 通过设置小圆点的样式，表示当前该页被选中
        holder.itemViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < holder.dotViewsList.size(); i++) {
                    if (i == position % picNums) {
                        (holder.dotViewsList.get(i)).setBackgroundResource(R.drawable.freshman_h_dot_selected);
                    } else {
                        (holder.dotViewsList.get(i)).setBackgroundResource(R.drawable.freshman_h_dot_unselected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setViewPagerScrollSpeed(holder.itemViewPager);
        ImagePagerAdapter1 adapter = new ImagePagerAdapter1(mContext, holder.itemViewPager, dataBean.getPictureList());
        holder.itemViewPager.setOffscreenPageLimit(2);
        holder.itemViewPager.setAdapter(adapter);
        holder.itemViewPager.setTimeOut(position % 2 == 0 ? 4000 : 5500);
        holder.itemViewPager.setHasData(true);
        holder.itemViewPager.startTimer();

        // 随机生成一个默认选中page
        int var = (picNums > 0) ? new Random().nextInt(picNums) : 0;
        holder.itemViewPager.setCurrentItem(var);
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout dotsLayout;
        private CircleViewPager itemViewPager;
        private TextView itemName;
        private TextView itemMsg;
        private List<View> dotViewsList = new ArrayList<>();

        ViewHolder(View itemView) {
            super(itemView);
            dotsLayout = itemView.findViewById(R.id.dots_layout);
            itemViewPager = itemView.findViewById(R.id.item_viewpager);
            itemName = itemView.findViewById(R.id.item_name_tv);
            itemMsg = itemView.findViewById(R.id.item_msg_tv);
        }
    }

    private void setViewPagerScrollSpeed(ViewPager viewPager) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

