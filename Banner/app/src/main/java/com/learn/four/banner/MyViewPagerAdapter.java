package com.learn.four.banner;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Create on 2019/07/21
 *
 * @author Four
 * @description
 */
public class MyViewPagerAdapter extends PagerAdapter {

    private List<View> frags;

    public MyViewPagerAdapter(List<View> frags) {
        this.frags = frags;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = frags.get(position%4);
        container.addView(view, position%4);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(frags.get(position%4));
    }
}
