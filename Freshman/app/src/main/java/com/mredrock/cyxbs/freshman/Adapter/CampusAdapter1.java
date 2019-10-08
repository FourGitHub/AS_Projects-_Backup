package com.mredrock.cyxbs.freshman.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.CustomView.CircleViewPager;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.UI.Fragment.CampusFrg1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/8/14 0014.
 * CampusAdapter1 对应 CampusFrg1里的 RecyclerView
 * 1-3-1、1-3-3、1-3-4、1-3-5、1-3-8
 */

public class CampusAdapter1 extends RecyclerView.Adapter<CampusAdapter1.ViewHolder> {
    private Context mContext;
    private int type;

    // 比如这是学生食堂这个Frg的数据，数据库中也只可能有一项
    private List<CampusBean.DataBean> mCampusBeanList;

    public CampusAdapter1(Context context, List<CampusBean.DataBean> campusBeanList, int type) {
        this.mContext = context;
        this.mCampusBeanList = campusBeanList;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.freshman_h_campus_item_1, parent, false);
        return new ViewHolder(view);
    }

    /*
    每个item被滚动到屏幕内时将会调用此方法绑定
    */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int picNums = mCampusBeanList.get(position).getPictureList().size();
        CampusBean.DataBean bean = mCampusBeanList.get(position);
        holder.itemMsg.setText(bean.getContent());
        holder.itemName.setText(bean.getName());
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

        setPriceVisibility(holder, bean.getProperty());

        setViewPagerScrollSpeed(holder.itemViewPager);

        ImagePagerAdapter1 adapter = new ImagePagerAdapter1(mContext, holder.itemViewPager, mCampusBeanList.get(position).getPictureList());
        holder.itemViewPager.setOffscreenPageLimit(2);
        holder.itemViewPager.setAdapter(adapter);
        holder.itemViewPager.setTimeOut(position % 2 == 0 ? 4000 : 5500);
        holder.itemViewPager.setHasData(true);
        holder.itemViewPager.startTimer();

        // 随机生成一个默认选中page

        int var = (picNums > 0) ? new Random().nextInt(picNums) : 0;

        holder.itemViewPager.setCurrentItem(var);
    }

    private void setPriceVisibility(ViewHolder holder, String price) {
        switch (type) {
            case CampusFrg1.TYPE_FOOD_FRG: {
                holder.itemPrice.setText("¥" + price + "(人)");
                holder.itemPrice.setVisibility(View.VISIBLE);
            }
            break;
            case CampusFrg1.TYPE_OTHER_FRG:
                holder.itemPrice.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mCampusBeanList == null ? 0 : mCampusBeanList.size();
    }

    private void setViewPagerScrollSpeed(ViewPager viewPager) {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout dotsLayout;
        private CircleViewPager itemViewPager;
        private TextView itemName;
        private TextView itemMsg;
        private TextView itemPrice;
        private List<View> dotViewsList = new ArrayList<>();

        ViewHolder(View itemView) {
            super(itemView);
            dotsLayout = itemView.findViewById(R.id.dots_layout);
            itemViewPager = itemView.findViewById(R.id.item_viewpager);
            itemName = itemView.findViewById(R.id.item_name_tv);
            itemMsg = itemView.findViewById(R.id.item_msg_tv);
            itemPrice = itemView.findViewById(R.id.item_price_tv);
        }
    }

}
