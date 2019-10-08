package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.Adapter.MyPagerAdapter;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.R;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14 0014.
 */

public class DormitoryFrg extends BaseFragment {
    private ViewPager dormitoryViewPager;
    private TabLayout dormitoryTabLayout;
    private String[] titles = new String[]{"明理苑", "宁静苑", "兴业苑", "知行苑"};


    @Override
    public int getContentViewId() {
        return R.layout.freshman_h_dormitory_frg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dormitoryViewPager = view.findViewById(R.id.dormitory_view_pager);
        dormitoryTabLayout = view.findViewById(R.id.dormitory_tab_layout);

        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), titles, MyPagerAdapter.DORMITORY_TABLAYOUT);
        dormitoryViewPager.setAdapter(adapter);
        dormitoryTabLayout.setupWithViewPager(dormitoryViewPager);
        dormitoryTabLayout.setTabMode(TabLayout.MODE_FIXED);

        // 通过自定义的TabLayout条目样式来修改条目字体大小（16dp）
        for (int i = 0; i < adapter.getCount(); i++) {
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.freshman_h_custom_tab, null);
            TextView textView = view1.findViewById(R.id.tab_item_tv);
            textView.setText(adapter.getPageTitle(i));
            dormitoryTabLayout.getTabAt(i).setCustomView(view1);
        }
        // 这里的两个20可能要改一下，到时候看效果
        dormitoryTabLayout.post(() -> setIndicator(dormitoryTabLayout, 20, 20));
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }


}
