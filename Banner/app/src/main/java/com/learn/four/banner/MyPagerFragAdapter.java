package com.learn.four.banner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Create on 2019/07/20
 *
 * @author Four
 * @description
 */
public class MyPagerFragAdapter extends FragmentStatePagerAdapter {
    public MyPagerFragAdapter(FragmentManager fm) {
        super(fm);
    }

    //    private List<MyPagerFrag> frags;
    //
    //    public MyPagerFragAdapter(FragmentManager fm, List<MyPagerFrag> frags) {
    //        super(fm);
    //        this.frags = frags;
    //    }

    @Override
    public Fragment getItem(int position) {
        //        return frags.get(position % 4);
        return MyPagerFrag.getInstance(position % 4);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
        //        return frags.size();
    }


}
