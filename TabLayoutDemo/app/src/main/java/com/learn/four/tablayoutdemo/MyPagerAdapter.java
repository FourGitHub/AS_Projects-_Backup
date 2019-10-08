package com.learn.four.tablayoutdemo;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Create on 2019/07/18
 *
 * @author Four
 * @description
 */
public class MyPagerAdapter extends PagerAdapter {
    List<View> pagers;

    public MyPagerAdapter(List<View> pagers) {
        this.pagers = pagers;
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View pagerItem = pagers.get(position);
        container.addView(pagerItem);
        return pagerItem;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(pagers.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "选项卡" + (position + 1);
    }
}
