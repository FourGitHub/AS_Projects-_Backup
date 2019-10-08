package com.mredrock.cyxbs.freshman.Adapter;

/**
 * Created by Administrator on 2018/8/12 0012.
 */

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.mredrock.cyxbs.freshman.CustomView.CircleViewPager;

public abstract class CirclePagerAdapter<V extends CircleViewPager> extends PagerAdapter {

    private static final int COEFFICIENT = 10;
    private V mViewPager;

    public CirclePagerAdapter(V viewPager) {
        this.mViewPager = viewPager;
    }

    /**
     * @return 实际数据数量
     */
    @IntRange(from = 0)
    public abstract int getRealDataCount();

    @Override
    public final int getCount() {
        long realDataCount = getRealDataCount();
        if (realDataCount > 1) {
            realDataCount = getRealDataCount() * COEFFICIENT;
            realDataCount = realDataCount > Integer.MAX_VALUE ? Integer.MAX_VALUE : realDataCount;
        }
        return (int) realDataCount;
    }

    @Override
    public final boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public final Object instantiateItem(@NonNull ViewGroup container, int position) {
        position = position % getRealDataCount();
        return this.instantiateRealItem(container, position);
    }

    public abstract Object instantiateRealItem(ViewGroup container, int position);

    @Override
    public final void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public final void finishUpdate(@NonNull ViewGroup container) {
        // 数量为1，不做position替换
        if (getCount() <= 1) {
            return;
        }

        int position = mViewPager.getCurrentItem();
        // ViewPager的更新即将完成，替换position，以达到无限循环的效果
        if (position == 0) {
            position = getRealDataCount();
            mViewPager.setCurrentItem(position, false);
        } else if (position == getCount() - 1) {
            position = getRealDataCount() - 1;
            mViewPager.setCurrentItem(position, false);
        }
    }


}
