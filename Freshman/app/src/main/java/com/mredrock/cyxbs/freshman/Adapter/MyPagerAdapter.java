package com.mredrock.cyxbs.freshman.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.mredrock.cyxbs.freshman.UI.Fragment.BaseFragment;
import com.mredrock.cyxbs.freshman.UI.Fragment.DXHDFrg;
import com.mredrock.cyxbs.freshman.UI.Fragment.DifficultestFrg;
import com.mredrock.cyxbs.freshman.UI.Fragment.DormitoryItemFrg;
import com.mredrock.cyxbs.freshman.UI.Fragment.OnlineFrg;
import com.mredrock.cyxbs.freshman.UI.Fragment.ProportionFrg;
import com.mredrock.cyxbs.freshman.UI.Fragment.XSZZFrg;

import java.util.List;


/**
 * Created by Administrator on 2018/8/12 0012.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private BaseFragment currentFrg;


    public static final int DORMITORY_TABLAYOUT = 2;
    public static final int DATA_TABLAYOUT = 3;
    public static final int CYFC_TABLAYOUT = 4;
    public static final int ONLINE_TABLAYOUT = 5;

    private int type = -100;

    private List<BaseFragment> mFragmentList;

    public MyPagerAdapter(FragmentManager fm, String[] titles, List<BaseFragment> mFragmentList) {
        super(fm);
        this.titles = titles;
        this.mFragmentList = mFragmentList;
    }

    public MyPagerAdapter(FragmentManager fm, String[] titles, int type) {
        super(fm);
        this.titles = titles;
        this.type = type;
    }


    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (type) {
            case DORMITORY_TABLAYOUT:
                return new DormitoryItemFrg(titles[position]);
            case DATA_TABLAYOUT:
                if (position == 0) {
                    return ProportionFrg.getInstance();
                } else if (position == 1) {
                    //最难科目
                    return DifficultestFrg.getInstance();
                }
                break;
            case CYFC_TABLAYOUT:
                if (position == 0) {
                    return new XSZZFrg();
                } else if (position == 1) {
                    return new DXHDFrg();
                }
                break;
            case ONLINE_TABLAYOUT:
                // 线上交流，getInstance()默认返回学院的OnlineFrg
                if (position == 0) {
                    return OnlineFrg.getInstance(OnlineFrg.TYPE_COLLEGE_GROUP);
                } else if (position == 1) {
                    return OnlineFrg.getInstance(OnlineFrg.TYPE_FELLOW_GROUP);
                }
                break;

        }
        return mFragmentList.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentFrg = (BaseFragment) object;
    }

    public BaseFragment getCurrentFragment() {
        return currentFrg;
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
