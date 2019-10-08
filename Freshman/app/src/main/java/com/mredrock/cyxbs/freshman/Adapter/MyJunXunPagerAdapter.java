package com.mredrock.cyxbs.freshman.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018/8/12 0012.
 *
 */

public class MyJunXunPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = new String[]{"军训风采", "小贴士"};
    private Fragment[] mFragments;

    public MyJunXunPagerAdapter(FragmentManager fm,Fragment[] mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
       return mFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

}
