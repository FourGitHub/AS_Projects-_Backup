package com.mredrock.cyxbs.freshman.UI.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.Adapter.MyPagerAdapter;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.UI.Fragment.OnlineFrg;


public class OnlineActivity extends BaseActivity implements View.OnClickListener {

    private View paddingView;
    private ImageView titleBackImv;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mTitleTv;
    private MyPagerAdapter adapter;
    private String[] titles = new String[]{"学院群","老乡群"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 和军训的布局一毛一样，所以直接复用了。
        setContentView(R.layout.freshman_h_activity_jun_xun);
       init();
        fusionStatusBar(paddingView);
    }

    private void init() {
        paddingView = findViewById(R.id.padding_view);
        titleBackImv = findViewById(R.id.title_back);
        mTitleTv = findViewById(R.id.title_tv);

        titleBackImv.setOnClickListener(this);
        mTitleTv.setText("线上交流");
        TextPaint tp = mTitleTv.getPaint();
        tp.setFakeBoldText(true);

        mViewPager = findViewById(R.id.view_pager);

        mTabLayout = findViewById(R.id.tab_layout);

        adapter = new MyPagerAdapter(getSupportFragmentManager(), titles, MyPagerAdapter.ONLINE_TABLAYOUT);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        // 通过自定义的TabLayout条目样式来修改条目字体大小（16dp）
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.freshman_h_custom_tab, null);
            TextView textView = view.findViewById(R.id.tab_item_tv);
            TextPaint tp1 = textView.getPaint();
            textView.setText(adapter.getPageTitle(i));
            tp1.setFakeBoldText(true);
            mTabLayout.getTabAt(i).setCustomView(view);
        }

//        mTabLayout.post(() -> setIndicator(mTabLayout, 40, 40));


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((OnlineFrg)adapter.getCurrentFragment()).initSearchEdt();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

}
