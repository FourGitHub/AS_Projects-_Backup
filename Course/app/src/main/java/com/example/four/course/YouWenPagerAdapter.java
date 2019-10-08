package com.example.four.course;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/5/26 0026.
 */

public class YouWenPagerAdapter extends PagerAdapter {
    private List<View> mViewList;

    public YouWenPagerAdapter() {

    }

    public YouWenPagerAdapter(List<View> viewList) {
        this.mViewList = viewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "全部";
            case 1:
                return "学习";
            case 2:
                return "生活";
            case 3:
                return "情感";
            case 4:
                return "其他";
            default:
                return null;
        }
    }
}
