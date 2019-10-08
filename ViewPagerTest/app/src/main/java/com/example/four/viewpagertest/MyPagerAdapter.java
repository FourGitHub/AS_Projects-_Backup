package com.example.four.viewpagertest;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18 0018.
 */

public class MyPagerAdapter extends PagerAdapter {

    private List<View> mViewList;

    public MyPagerAdapter(List<View> viewList) {
        this.mViewList = viewList;
    }

    /* 模拟器实测: 这个方法在滑动过程中会被疯狂的执行.而且会在Activity的onCreate()方法之前执行 */
    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position));
    }

    /* 关联了 TabLayout，重写此方法获取Pager对应的标签名 */
    @Override
    public CharSequence getPageTitle(int position) {
        return "标签 " + position;
    }
}
